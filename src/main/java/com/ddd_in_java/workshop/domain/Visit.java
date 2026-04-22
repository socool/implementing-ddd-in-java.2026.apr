package com.ddd_in_java.workshop.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public record Visit(Visitor visitor, LocalDate date, List<DroppedFraction> droppedFractions) {
    public String personId()    { return visitor.personId(); }
    public String city()        { return visitor.city(); }
    public String visitorType() { return visitor.type(); }

    public boolean inSameMonth(Visit other) {
        return this.yearMonth().equals(other.yearMonth());
    }

    public YearMonth yearMonth() {
        return YearMonth.from(this.date);
    }
}
