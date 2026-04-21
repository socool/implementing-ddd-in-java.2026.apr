package com.ddd_in_java.workshop.domain;

import java.util.ArrayList;
import java.util.List;

import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;

public class VisitHistory {

    private final String personId;
    private final List<Visit> visits = new ArrayList<>();
    private final FractionPriceCalculators priceCalculators;

    public VisitHistory(String personId) {
        this.personId = personId;
        this.priceCalculators = initPriceCalculators();
    }

    public VisitHistory(String personId, FractionPriceCalculators priceCalculators) {
        this.personId = personId;
        this.priceCalculators = priceCalculators;
    }

    private FractionPriceCalculators initPriceCalculators() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City",  GREEN,        "private"),  new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City",  CONSTRUCTION, "private"),  new FlatRatePriceCalculator(new Price(0.19, Currency.USD)));
        c.add(new PriceKey("Pineville", GREEN,        "private"),  new FlatRatePriceCalculator(new Price(0.10, Currency.USD)));
        c.add(new PriceKey("Pineville", CONSTRUCTION, "private"),  new FlatRatePriceCalculator(new Price(0.15, Currency.USD)));
        c.add(new PriceKey("Oak City",  GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City",  CONSTRUCTION, "business"), new FlatRatePriceCalculator(new Price(0.21, Currency.USD)));
        c.add(new PriceKey("Pineville", GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.12, Currency.USD)));
        c.add(new PriceKey("Pineville", CONSTRUCTION, "business"), new FlatRatePriceCalculator(new Price(0.13, Currency.USD)));
        return c;
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
