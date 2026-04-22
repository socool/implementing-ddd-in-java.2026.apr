package com.ddd_in_java.workshop.domain.invoicing;

import com.ddd_in_java.workshop.domain.events.DomainEvent;
import com.ddd_in_java.workshop.domain.events.PriceCalculated;
import com.ddd_in_java.workshop.domain.visitor.BusinessVisitor;
import com.ddd_in_java.workshop.domain.visitor.PrivateVisitor;

public class InvoiceHandler {
    private final InvoiceSender invoiceSender;

    public InvoiceHandler(InvoiceSender invoiceSender) {
        this.invoiceSender = invoiceSender;
    }

    public void handle(DomainEvent event) {
        if (event instanceof PriceCalculated(
                com.ddd_in_java.workshop.domain.visitor.Visitor visitor, com.ddd_in_java.workshop.domain.Price price
        )) {
            switch (visitor) {
                case BusinessVisitor bv -> invoiceSender.send(bv.email(), price);
                case PrivateVisitor pv -> {
                }
            }
        }
    }
}
