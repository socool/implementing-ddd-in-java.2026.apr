package com.ddd_in_java.workshop.domain;

import java.util.Optional;

public interface ExternalVisitors {
    Optional<ExternalVisitor> findById(String id);
}
