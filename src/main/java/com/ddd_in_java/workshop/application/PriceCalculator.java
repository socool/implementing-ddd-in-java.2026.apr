package com.ddd_in_java.workshop.application;

public class PriceCalculator {
  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    double totalPrice = request.dropped_fractions().stream().reduce(0.0,
            (price, droppedFraction) -> price
                    + (droppedFraction.fraction_type().equals("Green waste") ? 0.1 : 0.15) * droppedFraction.amount_dropped(),
            Double::sum);
    return new PriceCalculationResponse(totalPrice, "USD", request.visit_id(), request.person_id());
  }
}