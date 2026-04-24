package com.ddd_in_java.workshop.domain;

public record FlatRatePriceCalculation(Price ratePerKg) {
    public Price calculate(double weight) {
        return ratePerKg.times(weight);
    }
}
