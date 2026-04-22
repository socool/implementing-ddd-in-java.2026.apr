package com.ddd_in_java.workshop.domain;

public record Weight(double amount) {
    public Weight {
        if (amount < 0) throw new IllegalArgumentException("Amount must be positive");
    }

    public Weight add(Weight other) {
        return null;
    }

    public Weight subtract(Weight other) {
        return null;
    }
}
