package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.Currency;
import com.ddd_in_java.workshop.domain.FractionType;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.Weight;

public class PriceCalculator {
  public PriceCalculationResponse calculate(PriceCalculationRequest request) {
    var totalPrice = request.dropped_fractions().stream().reduce(
            new Price(0, Currency.USD),
            (price, dto) -> {
                var weight = new Weight(dto.amount_dropped());
                return price.add(FractionType.fromString(dto.fraction_type()).price().times(weight.amount()));
            },
            Price::sum);
    return new PriceCalculationResponse(totalPrice.amount(), totalPrice.currency().toString(), request.visit_id(), request.person_id());
  }
}