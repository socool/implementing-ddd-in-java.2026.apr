package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.*;

public class PriceCalculator {
  private final VisitHistories visitHistories;
  private final FractionPriceCalculators priceCalculators;
  private final ExternalVisitors externalVisitors;

  public PriceCalculator(VisitHistories visitHistories, FractionPriceCalculators priceCalculators, ExternalVisitors externalVisitors) {
    this.visitHistories = visitHistories;
    this.priceCalculators = priceCalculators;
    this.externalVisitors = externalVisitors;
  }

  public Price calculate(VisitRequest request) {
    ExternalVisitor visitor = externalVisitors.findById(request.personId()).orElseThrow();
    Visit visit = new Visit(visitor, request.date(), request.fractions());
    VisitHistory history = visitHistories.findByPersonId(visit.personId())
        .orElse(new VisitHistory(visit.personId(), priceCalculators));
    Price price = history.calculatePriceOfVisit(visit);
    visitHistories.save(history);
    return price;
  }
}
