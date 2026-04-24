package com.ddd_in_java.workshop.domain;

import java.util.List;

public record DroppedFraction(FractionType fractionType, Weight weight) {
    public Price calculatePrice() {
        throw new UnsupportedOperationException("Use calculatePrice with PricingRules");
    }

    public Price calculatePrice(VisitorType visitorType, PricingRules pricingRules) {
        return pricingRules.calculatePrice(PriceKey.from(fractionType, visitorType), weight.amount());
    }
}
