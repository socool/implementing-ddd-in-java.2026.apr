package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.DroppedFraction;

import java.time.LocalDate;
import java.util.List;

public record VisitRequest(String personId, List<DroppedFraction> fractions, LocalDate date) {}
