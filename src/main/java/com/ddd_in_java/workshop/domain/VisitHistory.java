package com.ddd_in_java.workshop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitHistory {

    private record YearlyPriceKey(PriceKey priceKey, int year) {}

    private final String personId;
    private final List<Visit> visits = new ArrayList<>();
    private final Map<YearlyPriceKey, Double> tierBasedUsageByYear = new HashMap<>();

    public VisitHistory(String personId) {
        this.personId = personId;
    }

    public String personId() {
        return personId;
    }

    public Price calculatePriceOfVisit(Visit visit, List<DroppedFraction> droppedFractions, String visitorType,
                                       PricingRules pricingRules) {
        VisitorType resolvedVisitorType = VisitorType.fromExternal(visitorType);
        visits.add(visit);
        var total = calculatePriceFor(visit, droppedFractions, resolvedVisitorType, pricingRules);
        if (resolvedVisitorType == VisitorType.PRIVATE && this.numberOfVisitsInSameMonth(visit) >= 3) {
            total = total.times(1.05);
        }
        return total;
    }

    int numberOfVisitsInSameMonth(Visit visit) {
        return (int) visits.stream().filter(v -> v.inSameMonth(visit)).count();
    }

    private Price calculatePriceFor(Visit visit, List<DroppedFraction> droppedFractions, VisitorType visitorType,
                                    PricingRules pricingRules) {
        return droppedFractions.stream()
            .map(fraction -> calculatePriceFor(visit, fraction, visitorType, pricingRules))
            .reduce(new Price(0, Currency.USD), Price::sum);
    }

    private Price calculatePriceFor(Visit visit, DroppedFraction fraction, VisitorType visitorType,
                                    PricingRules pricingRules) {
        PriceKey priceKey = PriceKey.from(fraction.fractionType(), visitorType);
        if (!pricingRules.usesTierBasedCalculation(priceKey)) {
            return fraction.calculatePrice(visitorType, pricingRules);
        }

        YearlyPriceKey yearlyPriceKey = new YearlyPriceKey(priceKey, visit.date().getYear());
        double alreadyUsed = tierBasedUsageByYear.getOrDefault(yearlyPriceKey, 0.0);
        double currentWeight = fraction.weight().amount();
        tierBasedUsageByYear.put(yearlyPriceKey, alreadyUsed + currentWeight);

        return pricingRules.calculatePrice(priceKey, currentWeight, alreadyUsed);
    }
}
