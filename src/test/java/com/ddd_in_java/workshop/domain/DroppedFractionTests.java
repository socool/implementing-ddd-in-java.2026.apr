package com.ddd_in_java.workshop.domain;

import com.ddd_in_java.workshop.application.Context;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedFractionTests {
    private final Context context = Context.initialize(id -> java.util.Optional.empty());

    @Test
    void correctPriceForConstructionWaste() {
        var fraction = new DroppedFraction(
            new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"),
            new Weight(10));
        assertEquals(new Price(10 * 0.15, Currency.USD), fraction.calculatePrice(VisitorType.PRIVATE, context.pricingRules));
    }

    @Test
    void sumOfMultipleFractionsWithPricingRules() {
        var fractions = List.of(
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"), new Weight(10)),
            new DroppedFraction(new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville"), new Weight(5))
        );
        var expected = new Price(10 * 0.15 + 5 * 0.15, Currency.USD);
        var total = fractions.stream()
            .map(fraction -> fraction.calculatePrice(VisitorType.PRIVATE, context.pricingRules))
            .reduce(new Price(0, Currency.USD), Price::sum);
        assertEquals(expected, total);
    }
}
