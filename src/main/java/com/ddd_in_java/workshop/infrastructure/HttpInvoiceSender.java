package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.domain.invoicing.InvoiceSender;
import com.ddd_in_java.workshop.domain.Price;
import org.springframework.web.client.RestClient;

public class HttpInvoiceSender implements InvoiceSender {

    private record InvoiceRequest(String email, double invoice_amount, String invoice_currency) {}

    private final RestClient restClient;

    public HttpInvoiceSender(String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public void send(String email, Price price) {
        restClient.post()
            .uri("/api/invoice")
            .body(new InvoiceRequest(email, price.amount(), price.currency().toString()))
            .retrieve()
            .toBodilessEntity();
    }
}
