package com.ddd_in_java.workshop.domain;

public record DroppedFraction(FractionType fractionType, Weight weight) {
    public Price calculatePrice() {
        return fractionType.price().times(weight.amount());
    }
}
