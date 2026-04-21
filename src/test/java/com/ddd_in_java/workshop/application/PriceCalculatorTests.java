package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final LocalDate JULY_24 = LocalDate.of(2023, 7, 24);
    private static final LocalDate JULY_25 = LocalDate.of(2023, 7, 25);
    private static final ExternalVisitor GUS = new ExternalVisitor("Squirrel Gus", "private", "", "Oak City");
    private static final ExternalVisitors GUS_VISITORS = id -> Optional.of(GUS);

    private static FractionPriceCalculators oakCityPrivatePrices() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City", GREEN,        "private"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City", CONSTRUCTION, "private"), new FlatRatePriceCalculator(new Price(0.19, Currency.USD)));
        return c;
    }

    private final List<DroppedFraction> fractions = List.of(
            new DroppedFraction(FractionType.fromString("Green waste"), new Weight(103))
    );

    @Test
    void appliesFivePercentFeeOnThirdVisitInSameMonth() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityPrivatePrices(), GUS_VISITORS);

        calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_23));
        calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_24));
        var price = calculator.calculate(new VisitRequest(GUS.id(), fractions, JULY_25));

        assertEquals(new Price(8.65, Currency.USD), price);
    }

    @Test
    void multipleFractionsInSingleVisit() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories(), oakCityPrivatePrices(), GUS_VISITORS);
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
}
