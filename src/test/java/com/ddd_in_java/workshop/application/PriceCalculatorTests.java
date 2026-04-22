package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.domain.invoicing.InvoiceHandler;
import com.ddd_in_java.workshop.domain.priceCalculation.FlatRatePriceCalculator;
import com.ddd_in_java.workshop.domain.priceCalculation.FractionPriceCalculators;
import com.ddd_in_java.workshop.domain.priceCalculation.PriceKey;
import com.ddd_in_java.workshop.domain.priceCalculation.TierBasedPriceCalculator;
import com.ddd_in_java.workshop.domain.visitor.BusinessVisitor;
import com.ddd_in_java.workshop.domain.visitor.ExternalVisitors;
import com.ddd_in_java.workshop.domain.visitor.PrivateVisitor;
import com.ddd_in_java.workshop.domain.visitor.Visitor;
import com.ddd_in_java.workshop.infrastructure.InMemoryMessageBus;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final LocalDate JULY_24 = LocalDate.of(2023, 7, 24);
    private static final LocalDate JULY_25 = LocalDate.of(2023, 7, 25);
    private static final Visitor GUS = new PrivateVisitor("Squirrel Gus", "Oak City");
    private static final ExternalVisitors GUS_VISITORS = id -> Optional.of(GUS);
    private static final MessageBus NO_OP_BUS = event -> {};

    private static FractionPriceCalculators oakCityPrivatePrices() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City", GREEN,        "private"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City", CONSTRUCTION, "private"), new FlatRatePriceCalculator(new Price(0.19, Currency.USD)));
        return c;
    }

    private static FractionPriceCalculators oakCityBusinessPrices() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City", GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City", CONSTRUCTION, "business"), new TierBasedPriceCalculator(1000,
            new FlatRatePriceCalculator(new Price(0.21, Currency.USD)),
            new FlatRatePriceCalculator(new Price(0.29, Currency.USD))));
        return c;
    }

    private static List<DroppedFraction> constructionFractions(double kg) {
        return List.of(new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(kg)));
    }

    private final List<DroppedFraction> fractions = List.of(
            new DroppedFraction(FractionType.fromString("Green waste"), new Weight(103))
    );

    @Test
    void appliesFivePercentFeeOnThirdVisitInSameMonth() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityPrivatePrices(), GUS_VISITORS, NO_OP_BUS);

        calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_23));
        calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_24));
        var price = calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_25));

        assertEquals(new Price(8.65, Currency.USD), price);
    }

    @Test
    void multipleFractionsInSingleVisit() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityPrivatePrices(), GUS_VISITORS, NO_OP_BUS);
        var multipleFractions = List.of(
                new DroppedFraction(FractionType.fromString("Green waste"), new Weight(83)),
                new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(18))
        );

        var price = calculator.calculate(new VisitRequest(GUS.id(), multipleFractions, JULY_25));

        // 0.08 * 83 = 6.64
        // 0.19 * 18 = 3.42
        // 3.42 + 6.64 = 10.06
        assertEquals(new Price(10.06, Currency.USD), price);
    }

    @Test
    void businessEmployees_shareExemption_byBusinessAddress() {
        var bertha = new BusinessVisitor("789 Business Ave", "Oak City", "");
        var bruce  = new BusinessVisitor("789 Business Ave", "Oak City", "");
        ExternalVisitors visitors = id -> switch (id) {
            case "Beaver Bertha" -> Optional.of(bertha);
            case "Beaver Bruce"  -> Optional.of(bruce);
            default -> Optional.empty();
        };
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityBusinessPrices(), visitors, NO_OP_BUS);

        var price1 = calculator.calculate(new VisitRequest("Beaver Bertha", constructionFractions(597),  JULY_23));
        var price2 = calculator.calculate(new VisitRequest("Beaver Bruce",  constructionFractions(1803), JULY_23));

        assertEquals(125.37, price1.amount(), 0.001);
        assertEquals(490.63, price2.amount(), 0.001); // shares Bertha's 597 kg: (403×0.21)+(1400×0.29)
    }

    @Test
    void sendsInvoice_forBusinessVisitor_afterPriceCalculation() {
        var bertha = new BusinessVisitor("789 Business Ave", "Oak City", "beavers@dam-building.com");
        ExternalVisitors visitors = id -> Optional.of(bertha);
        List<String> sentTo = new ArrayList<>();
        var bus = new InMemoryMessageBus();
        bus.subscribe(new InvoiceHandler((email, price) -> sentTo.add(email))::handle);
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityBusinessPrices(), visitors, bus);

        calculator.calculate(new VisitRequest("Beaver Bertha", constructionFractions(597), JULY_23));

        assertEquals(List.of("beavers@dam-building.com"), sentTo);
    }
}
