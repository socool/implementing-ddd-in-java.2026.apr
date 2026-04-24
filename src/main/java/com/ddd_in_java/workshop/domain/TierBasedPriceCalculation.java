package com.ddd_in_java.workshop.domain;

public record TierBasedPriceCalculation(double lowerTierLimit, Price lowerTierRate, Price upperTierRate) {
    public Price calculate(double weight, double amountAlreadyUsedInYear) {
        double lowerTierRemaining = Math.max(0, lowerTierLimit - amountAlreadyUsedInYear);
        double lowerTierWeight = Math.min(lowerTierRemaining, weight);
        double upperTierWeight = weight - lowerTierWeight;

        return lowerTierRate.times(lowerTierWeight).add(upperTierRate.times(upperTierWeight));
    }
}
