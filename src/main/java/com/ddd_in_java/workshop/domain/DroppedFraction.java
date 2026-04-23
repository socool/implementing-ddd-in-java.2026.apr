package com.ddd_in_java.workshop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DroppedFraction(
    @JsonProperty("amount_dropped") Double amountDropped,
    @JsonProperty("fraction_type") String fractionType
) {}
