package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitTests {

    private static final ExternalVisitor GUS = new ExternalVisitor("Squirrel Gus", "private", "", "Oak City");

    @Test
    void holdsPersonIdAndDate() {
        var visit = new Visit(GUS, LocalDate.of(2023, 7, 23), List.of());
        assertEquals("Squirrel Gus", visit.personId());
        assertEquals(LocalDate.of(2023, 7, 23), visit.date());
    }
}
