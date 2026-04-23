package com.ddd_in_java.workshop.application;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitHistoryTests {
    @Test
    void thirdVisitInSameMonthForSamePersonIsCountedAsThree() {
        VisitHistory history = new VisitHistory();
        history.record("Squirrel Gus", "1", LocalDate.of(2023, 7, 23));
        history.record("Squirrel Gus", "2", LocalDate.of(2023, 7, 24));

        assertEquals(3, history.visitNumberFor("Squirrel Gus", "3", LocalDate.of(2023, 7, 25)));
    }

    @Test
    void visitsResetForAnotherMonth() {
        VisitHistory history = new VisitHistory();
        history.record("Squirrel Gus", "1", LocalDate.of(2023, 7, 23));
        history.record("Squirrel Gus", "2", LocalDate.of(2023, 7, 24));
        history.record("Squirrel Gus", "3", LocalDate.of(2023, 7, 25));

        assertEquals(1, history.visitNumberFor("Squirrel Gus", "5", LocalDate.of(2023, 8, 12)));
    }

    @Test
    void visitsAreTrackedPerPerson() {
        VisitHistory history = new VisitHistory();
        history.record("Squirrel Gus", "1", LocalDate.of(2023, 7, 23));
        history.record("Squirrel Gus", "2", LocalDate.of(2023, 7, 24));

        assertEquals(1, history.visitNumberFor("Bald Eagle", "3", LocalDate.of(2023, 7, 24)));
    }
}
