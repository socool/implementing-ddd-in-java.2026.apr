package com.ddd_in_java.workshop.domain.priceCalculation;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.Weight;

public interface FractionPriceCalculator {
    Price calculate(DroppedFraction fraction);

    default Price calculate(DroppedFraction fraction, Weight previousWeightThisYear) {
        return calculate(fraction);
    }
}
