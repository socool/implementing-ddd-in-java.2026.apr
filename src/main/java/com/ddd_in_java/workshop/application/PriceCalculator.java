package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.FractionType;

public class PriceCalculator {
  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    double totalPrice = request.dropped_fractions().stream().reduce(0.0,
            (price, dto) -> price + pricePerKgFor(dto.fraction_type()) * dto.amount_dropped(),
            Double::sum);
    return new PriceCalculationResponse(totalPrice, "USD", request.visit_id(), request.person_id());
  }

  private double pricePerKgFor(String fractionType) {
    return FractionType.fromString(fractionType).price();
  }
}