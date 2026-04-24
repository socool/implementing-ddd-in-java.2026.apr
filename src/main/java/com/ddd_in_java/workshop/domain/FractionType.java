package com.ddd_in_java.workshop.domain;

public record FractionType(AllowedFractionType allowedFractionType, String city) {
    public enum AllowedFractionType { CONSTRUCTION, GREEN }

    public static FractionType fromString(String type, String city) {
        return switch (type) {
            case "Construction waste" -> new FractionType(AllowedFractionType.CONSTRUCTION, city);
            case "Green waste"        -> new FractionType(AllowedFractionType.GREEN, city);
            default -> throw new IllegalArgumentException("Invalid fraction type: " + type);
        };
    }

    public Price price() {
        throw new UnsupportedOperationException("Use priceFor with PricingRules");
    }

    public Price priceFor(VisitorType visitorType, PricingRules pricingRules) {
        return pricingRules.calculatePrice(PriceKey.from(this, visitorType), 1);
    }
}
