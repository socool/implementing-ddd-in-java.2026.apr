package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.VisitHistory;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PriceCalculator {
  private final VisitHistory visitHistory;

  public PriceCalculator(VisitHistory visitHistory) {
    this.visitHistory = visitHistory;
  }

  public Price calculate(List<DroppedFraction> fractions) {
    return DroppedFraction.sum(fractions);
  }

  public Price calculate(List<DroppedFraction> fractions, String personId, LocalDate date) {
    visitHistory.add(personId, date);
    var total = DroppedFraction.sum(fractions);
    if (visitHistory.numberOfVisitsIn(personId, YearMonth.from(date)) >= 3) {
      total = total.times(1.05);
    }
    return total;
  }
}
