package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitHistoryTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);

    @Test
    void firstVisitIsCounted() {
        var history = new VisitHistory("Squirrel Gus");
        history.calculatePriceOfVisit(new Visit("Squirrel Gus", JULY_23), List.of());
        assertEquals(1, history.numberOfVisitsInSameMonth(new Visit("Squirrel Gus", JULY_23)));
    }

    @Test
    void visitsInDifferentMonthAreNotCounted() {
        var history = new VisitHistory("Squirrel Gus");
        history.calculatePriceOfVisit(new Visit("Squirrel Gus", JULY_23), List.of());
        history.calculatePriceOfVisit(new Visit("Squirrel Gus", LocalDate.of(2023, 7, 24)), List.of());
        assertEquals(0, history.numberOfVisitsInSameMonth(new Visit("Squirrel Gus", LocalDate.of(2023, 8, 1))));
    }
}
