package com.ddd_in_java.workshop.domain;

public interface FractionPriceCalculator {
    Price calculate(DroppedFraction fraction);

    default Price calculate(DroppedFraction fraction, Weight previousWeightThisYear) {
        return calculate(fraction);
    }
}
