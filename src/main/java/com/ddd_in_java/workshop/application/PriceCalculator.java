package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.Price;

import java.util.List;

public class PriceCalculator {
  public Price calculate(List<DroppedFraction> fractions, String city) {
    return DroppedFraction.sum(fractions, city);
  }
}
