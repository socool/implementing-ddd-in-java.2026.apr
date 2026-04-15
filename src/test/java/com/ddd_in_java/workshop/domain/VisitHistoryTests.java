package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitHistoryTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final YearMonth JULY = YearMonth.of(2023, 7);

    @Test
    void firstVisitIsCounted() {
        var history = new VisitHistory();
        history.add(new Visit("Squirrel Gus", JULY_23));
        assertEquals(1, history.numberOfVisitsInSameMonth(new Visit("Squirrel Gus", JULY_23)));
    }

    @Test
    void visitsInDifferentMonthAreNotCounted() {
        var history = new VisitHistory();
        history.add(new Visit("Squirrel Gus", JULY_23));
        history.add(new Visit("Squirrel Gus", LocalDate.of(2023, 7, 24)));
        assertEquals(0, history.numberOfVisitsInSameMonth(new Visit("Squirrel Gus", LocalDate.of(2023, 8, 1))));
    }

    @Test
    void visitsForDifferentPersonAreNotCounted() {
        var history = new VisitHistory();
        history.add(new Visit("Squirrel Gus", JULY_23));
        history.add(new Visit("Squirrel Gus", LocalDate.of(2023, 7, 24)));
        assertEquals(0, history.numberOfVisitsInSameMonth(new Visit("Bald Eagle", JULY_23)));
    }
}
