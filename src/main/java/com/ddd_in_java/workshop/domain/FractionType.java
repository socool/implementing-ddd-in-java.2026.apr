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

    public double price() {
        return switch (allowedFractionType) {
            case CONSTRUCTION -> 0.15;
            case GREEN        -> 0.1;
        };
    }
}
