package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedFractionTests {
    @Test
    void correctPriceForConstructionWaste() {
        var fraction = new DroppedFraction(
            new FractionType(FractionType.AllowedFractionType.CONSTRUCTION),
            new Weight(10));
        assertEquals(new Price(10 * 0.15, Currency.USD), fraction.calculatePrice("Pineville"));
    }

    @Test
    void sumOfEmptyListIsZero() {
        assertEquals(new Price(0, Currency.USD), DroppedFraction.sum(List.of(), "Pineville"));
    }

    @Test
    void sumOfMultipleFractions() {
        var fractions = List.of(
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION), new Weight(10)),
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION), new Weight(5))
        );
        var expected = new Price(10 * 0.15 + 5 * 0.15, Currency.USD);
        assertEquals(expected, DroppedFraction.sum(fractions, "Pineville"));
    }

    @Test
    void oakCityUsesDifferentRates() {
        var fractions = List.of(
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.GREEN), new Weight(83)),
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION), new Weight(18))
        );

        assertEquals(new Price(10.06, Currency.USD), DroppedFraction.sum(fractions, "Oak City"));
    }
}
