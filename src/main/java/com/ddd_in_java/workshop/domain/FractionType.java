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
        return switch (city) {
            case "Oak City" -> switch (allowedFractionType) {
                case GREEN        -> new Price(0.08, Currency.USD);
                case CONSTRUCTION -> new Price(0.19, Currency.USD);
            };
            case "Pineville" -> switch (allowedFractionType) {
                case GREEN        -> new Price(0.1,  Currency.USD);
                case CONSTRUCTION -> new Price(0.15, Currency.USD);
            };
            default -> new Price(0, Currency.USD);
        };
    }
}
