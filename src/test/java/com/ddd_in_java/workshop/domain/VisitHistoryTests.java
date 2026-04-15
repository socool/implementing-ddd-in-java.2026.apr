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
        history.add("Squirrel Gus", JULY_23);
        assertEquals(1, history.numberOfVisitsIn("Squirrel Gus", JULY));
    }

    @Test
    void visitsInDifferentMonthAreNotCounted() {
        var history = new VisitHistory();
        history.add("Squirrel Gus", JULY_23);
        history.add("Squirrel Gus", LocalDate.of(2023, 7, 24));
        assertEquals(0, history.numberOfVisitsIn("Squirrel Gus", YearMonth.of(2023, 8)));
    }
}
