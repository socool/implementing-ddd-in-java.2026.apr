package com.ddd_in_java.workshop.domain;

import java.util.ArrayList;
import java.util.List;

public class VisitHistory {

    private final String personId;
    private final List<Visit> visits = new ArrayList<>();
    private final FractionPriceCalculators priceCalculators;

    public VisitHistory(String personId, FractionPriceCalculators priceCalculators) {
        this.personId = personId;
        this.priceCalculators = priceCalculators;
    }

    public String personId() {
        return personId;
    }

    public PriceCalculated calculatePriceOfVisit(Visit visit) {
        var total = visit.droppedFractions().stream()
            .map(fraction -> {
                var previousKg = previousWeightThisYear(visit, fraction.fractionType().allowedFractionType());
                var calculator = priceCalculators.find(
                    new PriceKey(visit.city(), fraction.fractionType().allowedFractionType(), visit.visitorType())
                ).orElseThrow();
                return calculator.calculate(fraction, previousKg);
            })
            .reduce(new Price(0, Currency.USD), Price::sum);
        visits.add(visit);
        return new PriceCalculated(visit.visitor(), applyFee(total, visit));
    }

    private Weight previousWeightThisYear(Visit currentVisit, FractionType.AllowedFractionType type) {
        return visits.stream()
            .filter(v -> v.yearMonth().getYear() == currentVisit.yearMonth().getYear())
            .flatMap(v -> v.droppedFractions().stream())
            .filter(f -> f.fractionType().allowedFractionType() == type)
            .map(DroppedFraction::weight)
            .reduce(new Weight(0), Weight::add);
    }

    private Price applyFee(Price price, Visit visit) {
        if (this.numberOfVisitsInSameMonth(visit) >= 3 && visit.visitorType().equals("private")) {
            return price.times(1.05);
        }
        return price;
    }

    int numberOfVisitsInSameMonth(Visit visit) {
        return (int) visits.stream().filter(v -> v.inSameMonth(visit)).count();
    }
}
