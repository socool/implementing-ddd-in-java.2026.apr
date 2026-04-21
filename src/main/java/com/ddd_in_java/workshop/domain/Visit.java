package com.ddd_in_java.workshop.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public record Visit(ExternalVisitor visitor, LocalDate date) {
    public String personId() { return visitor.id(); }
    public String city()     { return visitor.city(); }

    public boolean inSameMonth(Visit other) {
        return this.yearMonth().equals(other.yearMonth());
    }

    public YearMonth yearMonth() {
        return YearMonth.from(this.date);
    }
}
