package com.ddd_in_java.workshop.domain;

public record Weight(double amount) {
    public Weight {
        if (amount < 0) throw new IllegalArgumentException("Amount must be positive");
    }

    public Weight add(Weight other) {
        return new Weight(this.amount + other.amount);
    }

    public Weight subtract(Weight other) {
        return new Weight(Math.max(0, this.amount - other.amount));
    }
}
