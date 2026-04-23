package com.ddd_in_java.workshop.application;

import java.util.List;
import com.ddd_in_java.workshop.domain.DroppedFraction;

public record PriceCalculationRequest(
	String date,
	List<DroppedFraction> dropped_fractions,
	String person_id,
	String visit_id
) {}
