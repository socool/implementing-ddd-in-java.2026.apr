package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TierBasedPriceCalculatorTests {

    private static final FractionPriceCalculator BELOW = new FlatRatePriceCalculator(new Price(0.21, Currency.USD));
    private static final FractionPriceCalculator ABOVE  = new FlatRatePriceCalculator(new Price(0.29, Currency.USD));
    private static final TierBasedPriceCalculator CALCULATOR = new TierBasedPriceCalculator(1000, BELOW, ABOVE);

    private static DroppedFraction constructionWaste(double kg) {
        return new DroppedFraction(FractionType.fromString("Construction waste"), new Weight(kg));
    }

    @Test
    void entireDropWithinExemption() {
        // 597 kg, prev=0 → 597×0.21 = 125.37
        assertEquals(125.37, CALCULATOR.calculate(constructionWaste(597), new Weight(0)).amount());
    }

    @Test
    void dropSpansThreshold() {
        // 1803 kg, prev=597 → (403×0.21) + (1400×0.29) = 84.63 + 406.00 = 490.63
        assertEquals(490.63, CALCULATOR.calculate(constructionWaste(1803), new Weight(597)).amount());
    }

    @Test
    void entireDropAboveThreshold() {
        // 901 kg, prev=2400 → exemption exhausted → 901×0.29 = 261.29
        assertEquals(261.29, CALCULATOR.calculate(constructionWaste(901), new Weight(2400)).amount());
    }
}
