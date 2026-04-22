package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.Price;

public interface InvoiceSender {
    void send(String email, Price price);
}
