package com.ddd_in_java.workshop.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class VisitHistory {

    private final List<Visit> visits = new ArrayList<>();

    public void add(String personId, LocalDate date) {
        visits.add(new Visit(personId, date));
    }

    public int numberOfVisitsIn(String personId, YearMonth month) {
        return (int) visits.stream()
                .filter(v -> YearMonth.from(v.date()).equals(month))
                .count();
    }
}
