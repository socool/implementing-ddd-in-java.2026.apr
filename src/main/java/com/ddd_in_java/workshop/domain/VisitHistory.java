package com.ddd_in_java.workshop.domain;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class VisitHistory {

    private final List<Visit> visits = new ArrayList<>();

    public void add(Visit visit) {
        visits.add(visit);
    }

    public int numberOfVisitsIn(String personId, YearMonth month) {
        return (int) visits.stream()
                .filter(v -> v.personId().equals(personId))
                .filter(v -> YearMonth.from(v.date()).equals(month))
                .count();
    }
}
