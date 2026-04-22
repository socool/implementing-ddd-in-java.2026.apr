package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeightTests {
    @Test
    void weightCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(-1));
    }

    @Test
    void add_twoWeights() {
        assertEquals(new Weight(700), new Weight(300).add(new Weight(400)));
    }

    @Test
    void subtract_withRemainder() {
        assertEquals(new Weight(400), new Weight(1000).subtract(new Weight(600)));
    }

    @Test
    void subtract_clamps_to_zero() {
        assertEquals(new Weight(0), new Weight(600).subtract(new Weight(1000)));
    }
}
