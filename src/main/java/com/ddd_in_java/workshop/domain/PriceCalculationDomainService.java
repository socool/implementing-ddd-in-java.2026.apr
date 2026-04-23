package com.ddd_in_java.workshop.domain;

import java.util.List;

public class PriceCalculationDomainService {
    public Price calculate(List<DroppedFraction> droppedFractions) {
        double total = 0.0;
        if (droppedFractions != null) {
            for (DroppedFraction fraction : droppedFractions) {
                total += amountFor(fraction) * rateFor(fraction);
            }
        }
        return new Price(total, Currency.USD);
    }

    private double amountFor(DroppedFraction fraction) {
        if (fraction == null || fraction.amountDropped() == null) {
            return 0.0;
        }

        return fraction.amountDropped();
    }

    private double rateFor(DroppedFraction fraction) {
        if (fraction == null || fraction.fractionType() == null) {
            return 0.0;
        }

        return switch (fraction.fractionType()) {
            case "Green waste" -> 0.1;
            case "Construction waste" -> 0.15;
            default -> 0.0;
        };
    }
}
