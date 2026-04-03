package com.ddd_in_java.workshop.application;

public class PriceCalculator {
  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    return new PriceCalculationResponse(0, "USD", request.visit_id(), request.person_id());
  }
}