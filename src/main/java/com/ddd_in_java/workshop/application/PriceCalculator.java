package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.Visit;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitHistory;

import java.util.List;

public class PriceCalculator {
  private final VisitHistories visitHistories;

  public PriceCalculator(VisitHistories visitHistories) {
    this.visitHistories = visitHistories;
  }

  public Price calculate(List<DroppedFraction> fractions, Visit visit) {
    VisitHistory history = visitHistories.findByPersonId(visit.personId())
        .orElse(new VisitHistory(visit.personId()));
    Price price = history.calculatePriceOfVisit(visit, fractions);
    visitHistories.save(history);
    return price;
  }
}
