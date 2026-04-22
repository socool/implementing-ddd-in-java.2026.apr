package com.ddd_in_java.workshop.domain;

public record TierBasedPriceCalculator(double weightLimit,
                                        FractionPriceCalculator firstPriceCalculator,
                                        FractionPriceCalculator secondPriceCalculator)
        implements FractionPriceCalculator {

    public Price calculate(DroppedFraction fraction) {
        return calculate(fraction, new Weight(0));
    }

    public Price calculate(DroppedFraction fraction, Weight previousWeightThisYear) {
        var remaining      = new Weight(weightLimit).subtract(previousWeightThisYear);
        double amountForFirst  = Math.min(fraction.weight().amount(), remaining.amount());
        double amountForSecond = Math.max(0, fraction.weight().amount() - amountForFirst);
        var droppedForFirst  = new DroppedFraction(fraction.fractionType(), new Weight(amountForFirst));
        var droppedForSecond = new DroppedFraction(fraction.fractionType(), new Weight(amountForSecond));
        return firstPriceCalculator.calculate(droppedForFirst)
                   .add(secondPriceCalculator.calculate(droppedForSecond));
    }
}
