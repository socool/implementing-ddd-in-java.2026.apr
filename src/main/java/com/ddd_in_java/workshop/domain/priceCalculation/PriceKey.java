package com.ddd_in_java.workshop.domain.priceCalculation;

import com.ddd_in_java.workshop.domain.FractionType;

public record PriceKey(String city, FractionType.AllowedFractionType fractionType, String visitorType) {
}
