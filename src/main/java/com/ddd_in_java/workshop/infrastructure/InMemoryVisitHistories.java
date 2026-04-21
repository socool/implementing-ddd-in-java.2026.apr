package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitHistory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryVisitHistories implements VisitHistories {

    private final Map<String, VisitHistory> histories = new HashMap<>();

    @Override
    public Optional<VisitHistory> findByPersonId(String personId) {
        return Optional.ofNullable(histories.get(personId));
    }

    @Override
    public void save(VisitHistory visitHistory) {
        histories.put(visitHistory.personId(), visitHistory);
    }
}
