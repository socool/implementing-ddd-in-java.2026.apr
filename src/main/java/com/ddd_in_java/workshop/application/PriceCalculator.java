package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.visitor.ExternalVisitors;
import com.ddd_in_java.workshop.domain.priceCalculation.FractionPriceCalculators;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.events.PriceCalculated;
import com.ddd_in_java.workshop.domain.Visit;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitHistory;
import com.ddd_in_java.workshop.domain.visitor.Visitor;
import com.ddd_in_java.workshop.domain.VisitorNotFound;

public class PriceCalculator {
  private final VisitHistories visitHistories;
  private final FractionPriceCalculators priceCalculators;
  private final ExternalVisitors externalVisitors;
  private final MessageBus messageBus;

  public PriceCalculator(VisitHistories visitHistories, FractionPriceCalculators priceCalculators, ExternalVisitors externalVisitors, MessageBus messageBus) {
    this.visitHistories = visitHistories;
    this.priceCalculators = priceCalculators;
    this.externalVisitors = externalVisitors;
    this.messageBus = messageBus;
  }

  public Price calculate(VisitRequest request) {
    Visitor visitor = externalVisitors.findById(request.personId()).orElseThrow(() -> new VisitorNotFound(request.personId()));
    Visit visit = new Visit(visitor, request.date(), request.fractions());
    VisitHistory history = visitHistories.findByPersonId(visitor.id())
        .orElse(new VisitHistory(visitor.id(), priceCalculators));

    PriceCalculated event = history.calculatePriceOfVisit(visit);

    visitHistories.save(history);
    messageBus.publish(event);

    return event.price();
  }
}
