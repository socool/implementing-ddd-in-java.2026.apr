package com.ddd_in_java.workshop.application;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContextTests {

    @Test
    void contextHasVisitHistory() {
        var context = Context.initialize(id -> Optional.empty(), (email, price) -> {});
        assertNotNull(context.visitHistories);
    }
}
