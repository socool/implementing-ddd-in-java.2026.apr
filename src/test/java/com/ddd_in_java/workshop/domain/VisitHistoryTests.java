package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitHistoryTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final Visitor GUS = new PrivateVisitor("Squirrel Gus", "Oak City");

    private static FractionPriceCalculators oakCityPrices() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City", GREEN,        "private"),  new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City", CONSTRUCTION, "private"),  new FlatRatePriceCalculator(new Price(0.19, Currency.USD)));
        c.add(new PriceKey("Oak City", GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City", CONSTRUCTION, "business"), new TierBasedPriceCalculator(1000,
            new FlatRatePriceCalculator(new Price(0.21, Currency.USD)),
            new FlatRatePriceCalculator(new Price(0.29, Currency.USD))));
        return c;
    }

    @Test
    void firstVisitIsCounted() {
        var history = new VisitHistory("Squirrel Gus", oakCityPrices());
        history.calculatePriceOfVisit(new Visit(GUS, JULY_23, List.of()));
        assertEquals(1, history.numberOfVisitsInSameMonth(new Visit(GUS, JULY_23, List.of())));
    }

    @Test
    void calculatesCorrectPrice_forGreenWaste_inOakCity() {
        var visitor = new PrivateVisitor("id", "Oak City");
        var visit = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Green waste"), new Weight(83))
        ));
        assertEquals(6.64, new VisitHistory("id", oakCityPrices()).calculatePriceOfVisit(visit).amount());
    }

    @Test
    void businessCustomer_paysBusinessRate_forConstructionWaste_inOakCity() {
        var visitor = new BusinessVisitor("id", "Oak City");
        var visit = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(18))
        ));
        assertEquals(3.78, new VisitHistory("id", oakCityPrices()).calculatePriceOfVisit(visit).amount()); // 18 × 0.21
    }

    @Test
    void privateCustomer_with3VisitsInSameMonth_paysFee() {
        var history = new VisitHistory("Squirrel Gus", oakCityPrices());
        var visit1 = new Visit(GUS, JULY_23, List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        var visit2 = new Visit(GUS, LocalDate.of(2023, 7, 24), List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        var visit3 = new Visit(GUS, LocalDate.of(2023, 7, 25), List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        history.calculatePriceOfVisit(visit1);
        history.calculatePriceOfVisit(visit2);
        var price = history.calculatePriceOfVisit(visit3); // 3rd visit: 100 × 0.08 × 1.05 = 8.40
        assertEquals(8.40, price.amount(), 0.001);
    }

    @Test
    void businessCustomer_with3VisitsInSameMonth_doesNotPayFee() {
        var business = new BusinessVisitor("Biz", "Oak City");
        var history = new VisitHistory("Biz", oakCityPrices());
        var visit1 = new Visit(business, JULY_23, List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        var visit2 = new Visit(business, LocalDate.of(2023, 7, 24), List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        var visit3 = new Visit(business, LocalDate.of(2023, 7, 25), List.of(new DroppedFraction(FractionType.fromString("Green waste"), new Weight(100))));
        history.calculatePriceOfVisit(visit1);
        history.calculatePriceOfVisit(visit2);
        var price = history.calculatePriceOfVisit(visit3); // 3rd visit: 100 × 0.08 = 8.00 (no fee)
        assertEquals(8.00, price.amount(), 0.001);
    }

    @Test
    void businessCustomer_tieredRate_firstVisitBelowThreshold() {
        var visitor = new BusinessVisitor("id", "Oak City");
        var visit = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(597))
        ));
        // 597 kg, prev=0 → 597×0.21 = 125.37
        assertEquals(125.37, new VisitHistory("id", oakCityPrices()).calculatePriceOfVisit(visit).amount());
    }

    @Test
    void businessCustomer_tieredRate_secondVisitSpansThreshold() {
        var visitor = new BusinessVisitor("id", "Oak City");
        var history = new VisitHistory("id", oakCityPrices());
        var visit1 = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(597))
        ));
        var visit2 = new Visit(visitor, LocalDate.of(2023, 7, 24), List.of(
            new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(1803))
        ));
        history.calculatePriceOfVisit(visit1);
        // 1803 kg, prev=597 → (403×0.21) + (1400×0.29) = 84.63 + 406.00 = 490.63
        assertEquals(490.63, history.calculatePriceOfVisit(visit2).amount());
    }

    @Test
    void visitsInDifferentMonthAreNotCounted() {
        var history = new VisitHistory("Squirrel Gus", oakCityPrices());
        history.calculatePriceOfVisit(new Visit(GUS, JULY_23, List.of()));
        history.calculatePriceOfVisit(new Visit(GUS, LocalDate.of(2023, 7, 24), List.of()));
        assertEquals(0, history.numberOfVisitsInSameMonth(new Visit(GUS, LocalDate.of(2023, 8, 1), List.of())));
    }
}
