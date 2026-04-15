package com.ddd_in_java.workshop.domain;

public record FractionType(AllowedFractionType allowedFractionType) {
    public enum AllowedFractionType { CONSTRUCTION, GREEN }

    public static FractionType fromString(String type) {
        return switch (type) {
            case "Construction waste" -> new FractionType(AllowedFractionType.CONSTRUCTION);
            case "Green waste"        -> new FractionType(AllowedFractionType.GREEN);
            default -> throw new IllegalArgumentException("Invalid fraction type: " + type);
        };
    }

    public Price price() {
        return switch (allowedFractionType) {
            case CONSTRUCTION -> new Price(0.15, Currency.USD);
            case GREEN        -> new Price(0.1, Currency.USD);
        };
    }
}
