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
        return new Price(amount * factor, currency);
    }
}
