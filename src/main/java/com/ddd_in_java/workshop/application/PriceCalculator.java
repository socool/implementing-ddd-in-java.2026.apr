package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.Visit;
import com.ddd_in_java.workshop.domain.VisitHistory;

import java.util.List;

public class PriceCalculator {
  private final VisitHistory visitHistory;

  public PriceCalculator(VisitHistory visitHistory) {
    this.visitHistory = visitHistory;
  }

  public Price calculate(List<DroppedFraction> fractions, Visit visit) {
    visitHistory.add(visit);
    var total = DroppedFraction.sum(fractions);
    if (visitHistory.numberOfVisitsInSameMonth(visit) >= 3) {
      total = total.times(1.05);
    }
    return total;
  }
}
