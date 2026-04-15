package com.ddd_in_java.workshop.application;

public class PriceCalculator {
  private static final double GREEN_WASTE_PRICE_PER_KG = 0.1;
  private static final double CONSTRUCTION_WASTE_PRICE_PER_KG = 0.15;

  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    double totalPrice = request.dropped_fractions().stream().reduce(0.0,
            (price, droppedFraction) -> price
                    + (droppedFraction.fraction_type().equals("Green waste")
                        ? GREEN_WASTE_PRICE_PER_KG : CONSTRUCTION_WASTE_PRICE_PER_KG)
                    * droppedFraction.amount_dropped(),
            Double::sum);
    return new PriceCalculationResponse(totalPrice, "USD", request.visit_id(), request.person_id());
  }
}