package com.ddd_in_java.workshop.domain;

import java.util.Optional;

public interface VisitHistories {
    Optional<VisitHistory> findByPersonId(String personId);
    void save(VisitHistory visitHistory);
}
