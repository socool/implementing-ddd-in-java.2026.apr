package com.ddd_in_java.workshop.domain;

import org.junit.jupiter.api.Test;

import static com.ddd_in_java.workshop.domain.Currency.USD;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;
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

    @Test
    void constructionWastePrice() {
        assertEquals(new Price(0.15, USD), new FractionType(FractionType.AllowedFractionType.CONSTRUCTION).price());
    }

    @Test
    void greenWastePrice() {
        assertEquals(new Price(0.1, USD), new FractionType(FractionType.AllowedFractionType.GREEN).price());
    }

    @Test
    void greenWastePriceInOakCity() {
        assertEquals(new Price(0.08, USD), new FractionType(GREEN, "Oak City").price());
    }

    @Test
    void constructionWastePriceInOakCity() {
        assertEquals(new Price(0.19, USD), new FractionType(CONSTRUCTION, "Oak City").price());
    }
}
