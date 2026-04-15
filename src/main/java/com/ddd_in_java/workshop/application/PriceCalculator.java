package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.FractionType;
import com.ddd_in_java.workshop.domain.Weight;

public class PriceCalculator {
  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    var fractions = request.dropped_fractions().stream()
        .map(dto -> new DroppedFraction(
            FractionType.fromString(dto.fraction_type()),
            new Weight(dto.amount_dropped())))
        .toList();
    var totalPrice = DroppedFraction.sum(fractions);
    return new PriceCalculationResponse(totalPrice.amount(), totalPrice.currency().toString(), request.visit_id(), request.person_id());
  }
}