package com.ddd_in_java.workshop.domain;

public record TierBasedPriceCalculator(double weightLimit,
                                        FractionPriceCalculator firstPriceCalculator,
                                        FractionPriceCalculator secondPriceCalculator)
        implements FractionPriceCalculator {

    public Price calculate(DroppedFraction fraction) {
        return calculate(fraction, new Weight(0));
    }

    public Price calculate(DroppedFraction fraction, Weight previousWeightThisYear) {
        return null;
    }
}
