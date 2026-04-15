package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FractionTypeTests {
    @Test
    void validTypeDoesNotThrow() {
        assertDoesNotThrow(() -> FractionType.fromString("Construction waste"));
    }

    @Test
    void invalidTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> FractionType.fromString("Rubbish"));
    }
}
