package com.ddd_in_java.workshop.domain;

import java.util.List;

public record DroppedFraction(FractionType fractionType, Weight weight) {
    public Price calculatePrice(String city) {
        return fractionType.priceForCity(city).times(weight.amount());
    }

    public static Price sum(List<DroppedFraction> fractions, String city) {
        return fractions.stream()
            .map(fraction -> fraction.calculatePrice(city))
            .reduce(new Price(0, Currency.USD), Price::sum);
    }
}
