package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedFractionTests {
    @Test
    void holdsTypeAndWeight() {
        var fraction = new DroppedFraction(
            new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"),
            new Weight(10));
        assertEquals(FractionType.AllowedFractionType.CONSTRUCTION, fraction.fractionType().allowedFractionType());
        assertEquals(10, fraction.weight().amount());
    }
}
