package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.FractionPriceCalculators;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.Visit;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitHistory;

public class PriceCalculator {
  private final VisitHistories visitHistories;
  private final FractionPriceCalculators priceCalculators;

  public PriceCalculator(VisitHistories visitHistories, FractionPriceCalculators priceCalculators) {
    this.visitHistories = visitHistories;
    this.priceCalculators = priceCalculators;
  }

  public Price calculate(Visit visit) {
    VisitHistory history = visitHistories.findByPersonId(visit.personId())
        .orElse(new VisitHistory(visit.personId(), priceCalculators));
    Price price = history.calculatePriceOfVisit(visit);
    visitHistories.save(history);
    return price;
  }
}
