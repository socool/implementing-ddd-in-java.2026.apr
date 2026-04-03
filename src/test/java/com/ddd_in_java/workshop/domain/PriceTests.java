package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriceTests {
    @Test
    void priceCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Price(-1, Currency.USD));
    }

    @Test
    void testPriceAreEqual() {
        assertEquals(new Price(0, Currency.USD), new Price(0, Currency.USD));
    }

    @Test
    void pricesAreNotEqual() {
        assertNotEquals(new Price(0, Currency.USD), new Price(1, Currency.USD));
    }
}