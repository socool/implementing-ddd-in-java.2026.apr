package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.domain.invoicing.InvoiceHandler;
import com.ddd_in_java.workshop.domain.invoicing.InvoiceSender;
import com.ddd_in_java.workshop.domain.priceCalculation.FlatRatePriceCalculator;
import com.ddd_in_java.workshop.domain.priceCalculation.FractionPriceCalculators;
import com.ddd_in_java.workshop.domain.priceCalculation.PriceKey;
import com.ddd_in_java.workshop.domain.priceCalculation.TierBasedPriceCalculator;
import com.ddd_in_java.workshop.domain.visitor.ExternalVisitors;
import com.ddd_in_java.workshop.infrastructure.InMemoryMessageBus;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;

import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;

public class Context {
    public final ExternalVisitors externalVisitors;
    public final VisitHistories visitHistories = new InMemoryVisitHistories();
    public final FractionPriceCalculators priceCalculators = initPriceCalculators();
    public final MessageBus messageBus;

    private Context(ExternalVisitors externalVisitors, InvoiceSender invoiceSender) {
        this.externalVisitors = externalVisitors;
        var bus = new InMemoryMessageBus();
        bus.subscribe(new InvoiceHandler(invoiceSender)::handle);
        this.messageBus = bus;
    }

    public static Context initialize(ExternalVisitors externalVisitors, InvoiceSender invoiceSender) {
        return new Context(externalVisitors, invoiceSender);
    }

    private static FractionPriceCalculators initPriceCalculators() {
        var c = new FractionPriceCalculators();
        c.add(new PriceKey("Oak City",  GREEN,        "private"),  new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City",  CONSTRUCTION, "private"),  new FlatRatePriceCalculator(new Price(0.19, Currency.USD)));
        c.add(new PriceKey("Pineville", GREEN,        "private"),  new FlatRatePriceCalculator(new Price(0.10, Currency.USD)));
        c.add(new PriceKey("Pineville", CONSTRUCTION, "private"),  new FlatRatePriceCalculator(new Price(0.15, Currency.USD)));
        c.add(new PriceKey("Oak City",  GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.08, Currency.USD)));
        c.add(new PriceKey("Oak City",  CONSTRUCTION, "business"), new TierBasedPriceCalculator(1000,
                new FlatRatePriceCalculator(new Price(0.21, Currency.USD)),
                new FlatRatePriceCalculator(new Price(0.29, Currency.USD))));
        c.add(new PriceKey("Pineville", GREEN,        "business"), new FlatRatePriceCalculator(new Price(0.12, Currency.USD)));
        c.add(new PriceKey("Pineville", CONSTRUCTION, "business"), new FlatRatePriceCalculator(new Price(0.13, Currency.USD)));
        return c;
    }
}
