package com.ddd_in_java.workshop.domain;

public record FlatRatePriceCalculator(Price pricePerUnit) implements FractionPriceCalculator {
    public Price calculate(DroppedFraction fraction) {
        return null;
    }
}
