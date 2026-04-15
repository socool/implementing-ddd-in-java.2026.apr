package com.ddd_in_java.workshop.domain;

import java.time.LocalDate;

public record Visit(String personId, LocalDate date) {
}
