package com.ddd_in_java.workshop.presentation;

import java.time.LocalDate;
import java.util.List;

public record PriceCalculationRequest(String date, List<DroppedFractionRequest> dropped_fractions, String person_id,
                                      String visit_id) {

    public LocalDate localDate() {
        return LocalDate.parse(date);
    }
}
