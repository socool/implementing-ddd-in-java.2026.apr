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

        calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_23), "RESIDENT");
        calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_24), "RESIDENT");
        var price = calculator.calculate(fractions, new Visit("Squirrel Gus", JULY_25), "RESIDENT");

        assertEquals(new Price(8.65, Currency.USD), price);
    }

    @Test
    void appliesOakCityBusinessConstructionRateAcrossVisitsInSameYear() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories());
        var firstVisitFractions = List.of(
            new DroppedFraction(FractionType.fromString("Construction waste", "Oak City"), new Weight(600))
        );
        var secondVisitFractions = List.of(
            new DroppedFraction(FractionType.fromString("Construction waste", "Oak City"), new Weight(900))
        );

        var firstPrice = calculator.calculate(firstVisitFractions, new Visit("Acme Corp", JULY_23), "BUSINESS");
        var secondPrice = calculator.calculate(secondVisitFractions, new Visit("Acme Corp", JULY_24), "BUSINESS");

        assertEquals(new Price(126, Currency.USD), firstPrice);
        assertEquals(new Price(229, Currency.USD), secondPrice);
    }

    @Test
    void resetsOakCityBusinessConstructionExemptionInNewYear() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories());
        var fractions = List.of(
            new DroppedFraction(FractionType.fromString("Construction waste", "Oak City"), new Weight(900))
        );

        calculator.calculate(fractions, new Visit("Acme Corp", LocalDate.of(2023, 12, 31)), "BUSINESS");
        var januaryPrice = calculator.calculate(fractions, new Visit("Acme Corp", LocalDate.of(2024, 1, 1)), "BUSINESS");

        assertEquals(new Price(189, Currency.USD), januaryPrice);
    }

    @Test
    void doesNotApplyThirdVisitFeeToBusinessVisitors() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories());

        calculator.calculate(fractions, new Visit("Acme Corp", JULY_23), "BUSINESS");
        calculator.calculate(fractions, new Visit("Acme Corp", JULY_24), "BUSINESS");
        var price = calculator.calculate(fractions, new Visit("Acme Corp", JULY_25), "BUSINESS");

        assertEquals(new Price(8.24, Currency.USD), price);
    }

    @Test
    void recognizesBusinessVisitorsFromNonUppercaseTypeValues() {
        var calculator = new PriceCalculator(new InMemoryVisitHistories());
        var fractions = List.of(
            new DroppedFraction(FractionType.fromString("Construction waste", "Oak City"), new Weight(597))
        );

        var price = calculator.calculate(fractions, new Visit("Beaver Bertha", JULY_23), "business customer");

        assertEquals(new Price(125.37, Currency.USD), price);
    }
}
