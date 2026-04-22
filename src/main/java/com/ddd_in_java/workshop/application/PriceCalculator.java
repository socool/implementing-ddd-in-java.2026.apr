package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.BusinessVisitor;
import com.ddd_in_java.workshop.domain.ExternalVisitors;
import com.ddd_in_java.workshop.domain.FractionPriceCalculators;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.PrivateVisitor;
import com.ddd_in_java.workshop.domain.Visit;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitHistory;
import com.ddd_in_java.workshop.domain.Visitor;
import com.ddd_in_java.workshop.domain.VisitorNotFound;

public class PriceCalculator {
  private final VisitHistories visitHistories;
  private final FractionPriceCalculators priceCalculators;
  private final ExternalVisitors externalVisitors;
  private final InvoiceSender invoiceSender;

  public PriceCalculator(VisitHistories visitHistories, FractionPriceCalculators priceCalculators, ExternalVisitors externalVisitors, InvoiceSender invoiceSender) {
    this.visitHistories = visitHistories;
    this.priceCalculators = priceCalculators;
    this.externalVisitors = externalVisitors;
    this.invoiceSender = invoiceSender;
  }

  public Price calculate(VisitRequest request) {
    Visitor visitor = externalVisitors.findById(request.personId()).orElseThrow(() -> new VisitorNotFound(request.personId()));
    Visit visit = new Visit(visitor, request.date(), request.fractions());
    VisitHistory history = visitHistories.findByPersonId(visitor.id())
        .orElse(new VisitHistory(visitor.id(), priceCalculators));

    Price price = history.calculatePriceOfVisit(visit);

    visitHistories.save(history);

    switch (visitor) {
      case BusinessVisitor bv -> invoiceSender.send(bv.email(), price);
      case PrivateVisitor pv  -> {}
    }

    return price;
  }
}
