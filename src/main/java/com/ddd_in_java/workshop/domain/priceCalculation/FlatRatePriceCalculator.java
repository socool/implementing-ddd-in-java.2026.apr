package com.ddd_in_java.workshop.domain.priceCalculation;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;

public record FlatRatePriceCalculator(Price pricePerUnit) implements FractionPriceCalculator {
    public Price calculate(DroppedFraction fraction) {
        return pricePerUnit.times(fraction.weight().amount());
    }
}
