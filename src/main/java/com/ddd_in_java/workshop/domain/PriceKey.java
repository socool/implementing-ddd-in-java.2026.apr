package com.ddd_in_java.workshop.domain;

public record PriceKey(String city, FractionType.AllowedFractionType fractionType, VisitorType visitorType) {
    public static PriceKey from(FractionType fractionType, VisitorType visitorType) {
        return new PriceKey(fractionType.city(), fractionType.allowedFractionType(), visitorType);
    }
}
