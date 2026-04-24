package com.ddd_in_java.workshop.domain;

import com.ddd_in_java.workshop.application.Context;
import org.junit.jupiter.api.Test;

import static com.ddd_in_java.workshop.domain.Currency.USD;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;
import static org.junit.jupiter.api.Assertions.*;

public class FractionTypeTests {
    private final Context context = Context.initialize(id -> java.util.Optional.empty());

    @Test
    void validTypeDoesNotThrow() {
        assertDoesNotThrow(() -> FractionType.fromString("Construction waste", "Pineville"));
    }

    @Test
    void invalidTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> FractionType.fromString("Rubbish", "Pineville"));
    }

    @Test
    void constructionWastePrice() {
        assertEquals(new Price(0.15, USD), new FractionType(FractionType.AllowedFractionType.CONSTRUCTION, "Pineville")
            .priceFor(VisitorType.PRIVATE, context.pricingRules));
    }

    @Test
    void greenWastePrice() {
        assertEquals(new Price(0.1, USD), new FractionType(FractionType.AllowedFractionType.GREEN, "Pineville")
            .priceFor(VisitorType.PRIVATE, context.pricingRules));
    }

    @Test
    void greenWastePriceInOakCity() {
        assertEquals(new Price(0.08, USD), new FractionType(GREEN, "Oak City")
            .priceFor(VisitorType.PRIVATE, context.pricingRules));
    }

    @Test
    void constructionWastePriceInOakCity() {
        assertEquals(new Price(0.19, USD), new FractionType(CONSTRUCTION, "Oak City")
            .priceFor(VisitorType.PRIVATE, context.pricingRules));
    }
}
