package com.ddd_in_java.workshop.application;

public record PriceCalculationResponse(double price_amount, String price_currency, String visit_id, String person_id) {
    
}