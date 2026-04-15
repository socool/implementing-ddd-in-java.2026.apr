package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedFractionTests {
    @Test
    void correctPriceForConstructionWaste() {
        var fraction = new DroppedFraction(
            new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"),
            new Weight(10));
        assertEquals(new Price(10 * 0.15, Currency.USD), fraction.calculatePrice());
    }

    @Test
    void sumOfEmptyListIsZero() {
        assertEquals(new Price(0, Currency.USD), DroppedFraction.sum(List.of()));
    }

    @Test
    void sumOfMultipleFractions() {
        var fractions = List.of(
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"), new Weight(10)),
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"), new Weight(5))
        );
        var expected = new Price(10 * 0.15 + 5 * 0.15, Currency.USD);
        assertEquals(expected, DroppedFraction.sum(fractions));
    }
}
