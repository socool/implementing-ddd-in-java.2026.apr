package com.ddd_in_java.workshop.domain;

import java.util.List;

public record DroppedFraction(FractionType fractionType, Weight weight) {
    public Price calculatePrice() {
        return fractionType.price().times(weight.amount());
    }

    public static Price sum(List<DroppedFraction> fractions) {
        return fractions.stream()
            .map(DroppedFraction::calculatePrice)
            .reduce(new Price(0, Currency.USD), Price::sum);
    }
}
