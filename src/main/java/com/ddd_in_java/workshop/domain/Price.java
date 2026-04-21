package com.ddd_in_java.workshop.domain;

import java.util.Objects;

public record Price(double amount, Currency currency) {
    public Price {
        Objects.requireNonNull(currency);
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public Price times(double factor) {
        return new Price(round2DecimalPlaces(amount * factor), currency);
    }

    public Price add(Price other) {
        return new Price(round2DecimalPlaces(amount + other.amount), currency);
    }

    public static Price sum(Price left, Price right) {
        return left.add(right);
    }

    private double round2DecimalPlaces(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
