package com.ddd_in_java.workshop.domain;

import java.util.ArrayList;
import java.util.List;

public class VisitHistory {

    private final List<Visit> visits = new ArrayList<>();

    public Price calculatePriceOfVisit(Visit visit, List<DroppedFraction> droppedFractions) {
        visits.add(visit);
        var total = DroppedFraction.sum(droppedFractions);
        if (this.numberOfVisitsInSameMonth(visit) >= 3) {
            total = total.times(1.05);
        }
        return total;
    }

    int numberOfVisitsInSameMonth(Visit visit) {
        return (int) visits.stream().filter(v -> v.inSameMonth(visit)).count();
    }
}
