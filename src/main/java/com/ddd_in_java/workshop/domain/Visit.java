package com.ddd_in_java.workshop.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public record Visit(String personId, LocalDate date) {
    public boolean inSameMonth(Visit other) {
        return this.personId.equals(other.personId) &&
                YearMonth.from(this.date).equals(YearMonth.from(other.date));
    }
}
