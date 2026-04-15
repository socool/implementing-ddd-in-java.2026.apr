package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitTests {

    @Test
    void holdsPersonIdAndDate() {
        var visit = new Visit("Squirrel Gus", LocalDate.of(2023, 7, 23));
        assertEquals("Squirrel Gus", visit.personId());
        assertEquals(LocalDate.of(2023, 7, 23), visit.date());
    }
}
