package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.visitor.BusinessVisitor;
import com.ddd_in_java.workshop.domain.events.DomainEvent;
import com.ddd_in_java.workshop.domain.events.PriceCalculated;
import com.ddd_in_java.workshop.domain.visitor.PrivateVisitor;

public class InvoiceSubscriber {

    private final InvoiceSender invoiceSender;

    public InvoiceSubscriber(InvoiceSender invoiceSender) {
        this.invoiceSender = invoiceSender;
    }

    public void on(DomainEvent event) {
        if (event instanceof PriceCalculated e) {
            switch (e.visitor()) {
                case BusinessVisitor bv -> invoiceSender.send(bv.email(), e.price());
                case PrivateVisitor pv  -> {}
            }
        }
    }
}
