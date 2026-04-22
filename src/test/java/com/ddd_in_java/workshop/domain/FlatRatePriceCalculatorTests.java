package com.ddd_in_java.workshop.domain;

import com.ddd_in_java.workshop.domain.priceCalculation.FlatRatePriceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlatRatePriceCalculatorTests {

    @Test
    void calculatesPrice_forGreenWaste() {
        var calculator = new FlatRatePriceCalculator(new Price(0.08, Currency.USD));
        var fraction = new DroppedFraction(FractionType.fromString("Green waste"), new Weight(83));
        assertEquals(6.64, calculator.calculate(fraction).amount());
    }

    @Test
    void calculatesPrice_forConstructionWaste() {
        var calculator = new FlatRatePriceCalculator(new Price(0.21, Currency.USD));
        var fraction = new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(18));
        assertEquals(3.78, calculator.calculate(fraction).amount());
    }
}
