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

    public Price calculatePriceOfVisit(Visit visit) {
        visits.add(visit);
        var total = visit.droppedFractions().stream()
            .reduce(new Price(0, Currency.USD), (price, fraction) -> {
                var calculator = priceCalculators.find(
                    new PriceKey(visit.city(), fraction.fractionType().allowedFractionType(), visit.visitorType())
                ).orElseThrow();
                return price.add(calculator.calculate(fraction));
            }, Price::sum);
        if (this.numberOfVisitsInSameMonth(visit) >= 3) {
            total = total.times(1.05);
        }
        return total;
    }

    int numberOfVisitsInSameMonth(Visit visit) {
        return (int) visits.stream().filter(v -> v.inSameMonth(visit)).count();
    }
}
