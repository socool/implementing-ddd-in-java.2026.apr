package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeightTests {
    @Test
    void weightCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(-1));
    }
}
