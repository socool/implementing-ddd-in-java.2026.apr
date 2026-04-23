package com.ddd_in_java.workshop.application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VisitHistory {
    private final Map<String, Map<YearMonth, Set<String>>> visits = new HashMap<>();

    public int visitNumberFor(String personId, String visitId, LocalDate date) {
        Set<String> monthlyVisits = visits
            .computeIfAbsent(personId, ignored -> new HashMap<>())
            .computeIfAbsent(YearMonth.from(date), ignored -> new HashSet<>());

        if (monthlyVisits.contains(visitId)) {
            return monthlyVisits.size();
        }

        return monthlyVisits.size() + 1;
    }

    public void record(String personId, String visitId, LocalDate date) {
        visits
            .computeIfAbsent(personId, ignored -> new HashMap<>())
            .computeIfAbsent(YearMonth.from(date), ignored -> new HashSet<>())
            .add(visitId);
    }
}
