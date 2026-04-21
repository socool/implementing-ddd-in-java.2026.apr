package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitHistoryTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final ExternalVisitor GUS = new ExternalVisitor("Squirrel Gus", "private", "", "Oak City");

    @Test
    void firstVisitIsCounted() {
        var history = new VisitHistory("Squirrel Gus");
        history.calculatePriceOfVisit(new Visit(GUS, JULY_23, List.of()));
        assertEquals(1, history.numberOfVisitsInSameMonth(new Visit(GUS, JULY_23, List.of())));
    }

    @Test
    void calculatesCorrectPrice_forGreenWaste_inOakCity() {
        var visitor = new ExternalVisitor("id", "private", "addr", "Oak City");
        var visit = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Green waste"), new Weight(83))
        ));
        assertEquals(6.64, new VisitHistory("id").calculatePriceOfVisit(visit).amount());
    }

    @Test
    void businessCustomer_paysBusinessRate_forConstructionWaste_inOakCity() {
        var visitor = new ExternalVisitor("id", "business", "addr", "Oak City");
        var visit = new Visit(visitor, JULY_23, List.of(
            new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(18))
        ));
        assertEquals(3.78, new VisitHistory("id").calculatePriceOfVisit(visit).amount()); // 18 × 0.21
    }

    @Test
    void visitsInDifferentMonthAreNotCounted() {
        var history = new VisitHistory("Squirrel Gus");
        history.calculatePriceOfVisit(new Visit(GUS, JULY_23, List.of()));
        history.calculatePriceOfVisit(new Visit(GUS, LocalDate.of(2023, 7, 24), List.of()));
        assertEquals(0, history.numberOfVisitsInSameMonth(new Visit(GUS, LocalDate.of(2023, 8, 1), List.of())));
    }
}
