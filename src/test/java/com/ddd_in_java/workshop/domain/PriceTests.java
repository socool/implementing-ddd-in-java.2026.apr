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

    @Test
    void times() {
        assertEquals(new Price(1.5, Currency.USD), new Price(0.15, Currency.USD).times(10));
    }

    @Test
    void add() {
        assertEquals(new Price(7, Currency.USD), new Price(2, Currency.USD).add(new Price(5, Currency.USD)));
    }

    @Test
    void amountIsRoundedToTwoDecimals() {
        assertEquals(new Price(8.65, Currency.USD), new Price(8.24, Currency.USD).times(1.05));
    }
}
