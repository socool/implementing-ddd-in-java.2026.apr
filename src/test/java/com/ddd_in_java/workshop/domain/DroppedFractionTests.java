package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedFractionTests {
    @Test
    void correctPriceForConstructionWaste() {
        var fraction = new DroppedFraction(
            new FractionType(FractionType.AllowedFractionType.CONSTRUCTION),
            new Weight(10));
        assertEquals(new Price(10 * 0.15, Currency.USD), fraction.calculatePrice());
    }
}
