package com.ddd_in_java.workshop.domain;

public record FractionType(AllowedFractionType allowedFractionType) {
    public enum AllowedFractionType { CONSTRUCTION, GREEN }
    private static final String OAK_CITY = "Oak City";
    private static final String PINEVILLE = "Pineville";

    public static FractionType fromString(String type) {
        return switch (type) {
            case "Construction waste" -> new FractionType(AllowedFractionType.CONSTRUCTION);
            case "Green waste"        -> new FractionType(AllowedFractionType.GREEN);
            default -> throw new IllegalArgumentException("Invalid fraction type: " + type);
        };
    }

    public Price priceForCity(String city) {
        return switch (city) {
            case OAK_CITY -> switch (allowedFractionType) {
                case CONSTRUCTION -> new Price(0.19, Currency.USD);
                case GREEN        -> new Price(0.08, Currency.USD);
            };
            case PINEVILLE -> switch (allowedFractionType) {
                case CONSTRUCTION -> new Price(0.15, Currency.USD);
                case GREEN        -> new Price(0.1, Currency.USD);
            };
            default -> throw new IllegalArgumentException("Unsupported city: " + city);
        };
    }
}
