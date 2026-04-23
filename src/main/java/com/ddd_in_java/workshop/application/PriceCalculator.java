package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.PriceCalculationDomainService;
import com.ddd_in_java.workshop.domain.Price;

public class PriceCalculator {
    private final PriceCalculationDomainService domainService = new PriceCalculationDomainService();

    public PriceCalculationResponse calculate(PriceCalculationRequest request) {
        Price price = domainService.calculate(request.dropped_fractions());
        return new PriceCalculationResponse(
            price.amount(),
            price.currency().name(),
            request.visit_id(),
            request.person_id()
        );
    }
}
