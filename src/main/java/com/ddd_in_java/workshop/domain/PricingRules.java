package com.ddd_in_java.workshop.domain;

import java.util.Map;

public final class PricingRules {

    private final Map<PriceKey, FlatRatePriceCalculation> flatRateRules;
    private final Map<PriceKey, TierBasedPriceCalculation> tierBasedRules;

    public PricingRules(Map<PriceKey, FlatRatePriceCalculation> flatRateRules,
                        Map<PriceKey, TierBasedPriceCalculation> tierBasedRules) {
        this.flatRateRules = flatRateRules;
        this.tierBasedRules = tierBasedRules;
    }

    public Price calculatePrice(PriceKey key, double weight) {
        return flatRateRules.getOrDefault(
            key, new FlatRatePriceCalculation(new Price(0, Currency.USD))
        ).calculate(weight);
    }

    public Price calculatePrice(PriceKey key, double weight, double amountAlreadyUsedInYear) {
        var tierBasedRule = tierBasedRules.get(key);
        if (tierBasedRule != null) {
            return tierBasedRule.calculate(weight, amountAlreadyUsedInYear);
        }
        return calculatePrice(key, weight);
    }

    public boolean usesTierBasedCalculation(PriceKey key) {
        return tierBasedRules.containsKey(key);
    }
}
