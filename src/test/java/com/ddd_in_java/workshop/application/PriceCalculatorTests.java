package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculatorTests {

    private static final LocalDate JULY_23 = LocalDate.of(2023, 7, 23);
    private static final LocalDate JULY_24 = LocalDate.of(2023, 7, 24);
    private static final LocalDate JULY_25 = LocalDate.of(2023, 7, 25);

    private final List<DroppedFraction> fractions = List.of(
            new DroppedFraction(FractionType.fromString("Green waste", "Oak City"), new Weight(103))
    );

    @Test
    void appliesFivePercentFeeOnThirdVisitInSameMonth() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories());

        calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_23));
        calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_24));
        var price = calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_25));

        assertEquals(new Price(8.65, Currency.USD), price);
    }
}
