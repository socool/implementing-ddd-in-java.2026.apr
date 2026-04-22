package com.ddd_in_java.workshop.domain.priceCalculation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FractionPriceCalculators {

    private final Map<PriceKey, FractionPriceCalculator> calculators = new HashMap<>();

    public void add(PriceKey key, FractionPriceCalculator calculator) {
        calculators.put(key, calculator);
    }

    public Optional<FractionPriceCalculator> find(PriceKey key) {
        return Optional.ofNullable(calculators.get(key));
    }
}
