package com.ddd_in_java.workshop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VisitHistory {

    private final String personId;
    private final List<Visit> visits = new ArrayList<>();
    private final Map<Integer, Double> businessConstructionWasteByYear = new HashMap<>();

    public VisitHistory(String personId) {
        this.personId = personId;
    }

    public String personId() {
        return personId;
    }

    public Price calculatePriceOfVisit(Visit visit, List<DroppedFraction> droppedFractions, String visitorType) {
        visits.add(visit);
        var total = businessPriceFor(visit, droppedFractions, visitorType);
        if (isPrivateVisitor(visitorType) && this.numberOfVisitsInSameMonth(visit) >= 3) {
            total = total.times(1.05);
        }
        return total;
    }

    int numberOfVisitsInSameMonth(Visit visit) {
        return (int) visits.stream().filter(v -> v.inSameMonth(visit)).count();
    }

    private Price businessPriceFor(Visit visit, List<DroppedFraction> droppedFractions, String visitorType) {
        if (!isBusinessVisitor(visitorType)) {
            return DroppedFraction.sum(droppedFractions);
        }

        return droppedFractions.stream()
            .map(fraction -> businessPriceFor(visit, fraction))
            .reduce(new Price(0, Currency.USD), Price::sum);
    }

    private Price businessPriceFor(Visit visit, DroppedFraction fraction) {
        if (!isOakCityConstructionWaste(fraction)) {
            return fraction.calculatePrice();
        }

        int year = visit.date().getYear();
        double alreadyDropped = businessConstructionWasteByYear.getOrDefault(year, 0.0);
        double currentWeight = fraction.weight().amount();
        double discountedWeight = Math.max(0, Math.min(1000 - alreadyDropped, currentWeight));
        double fullRateWeight = currentWeight - discountedWeight;
        businessConstructionWasteByYear.put(year, alreadyDropped + currentWeight);

        return new Price(discountedWeight * 0.21 + fullRateWeight * 0.29, Currency.USD);
    }

    private boolean isOakCityConstructionWaste(DroppedFraction fraction) {
        return fraction.fractionType().allowedFractionType() == FractionType.AllowedFractionType.CONSTRUCTION
            && fraction.fractionType().city().equals("Oak City");
    }

    private boolean isPrivateVisitor(String visitorType) {
        return !isBusinessVisitor(visitorType);
    }

    private boolean isBusinessVisitor(String visitorType) {
        if (visitorType == null) {
            return false;
        }

        return visitorType.trim().toUpperCase(Locale.ROOT).contains("BUSINESS");
    }
}
