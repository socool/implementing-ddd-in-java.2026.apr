package com.ddd_in_java.workshop.application;

import com.ddd_in_java.workshop.domain.ExternalVisitors;
import com.ddd_in_java.workshop.domain.FlatRatePriceCalculation;
import com.ddd_in_java.workshop.domain.Price;
import com.ddd_in_java.workshop.domain.PriceKey;
import com.ddd_in_java.workshop.domain.PricingRules;
import com.ddd_in_java.workshop.domain.TierBasedPriceCalculation;
import com.ddd_in_java.workshop.domain.VisitHistories;
import com.ddd_in_java.workshop.domain.VisitorType;
import com.ddd_in_java.workshop.infrastructure.InMemoryVisitHistories;

import java.util.Map;

import static com.ddd_in_java.workshop.domain.Currency.USD;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.CONSTRUCTION;
import static com.ddd_in_java.workshop.domain.FractionType.AllowedFractionType.GREEN;

public class Context {
    public final ExternalVisitors externalVisitors;
    public final VisitHistories visitHistories = new InMemoryVisitHistories();
    public final PricingRules pricingRules;

    private Context(ExternalVisitors externalVisitors, PricingRules pricingRules) {
        this.externalVisitors = externalVisitors;
        this.pricingRules = pricingRules;
    }

    public static Context initialize(ExternalVisitors externalVisitors) {
        Map<PriceKey, FlatRatePriceCalculation> flatRateRules = Map.of(
            new PriceKey("Oak City", GREEN, VisitorType.PRIVATE), new FlatRatePriceCalculation(new Price(0.08, USD)),
            new PriceKey("Oak City", CONSTRUCTION, VisitorType.PRIVATE), new FlatRatePriceCalculation(new Price(0.19, USD)),
            new PriceKey("Oak City", GREEN, VisitorType.BUSINESS), new FlatRatePriceCalculation(new Price(0.08, USD)),
            new PriceKey("Pineville", GREEN, VisitorType.PRIVATE), new FlatRatePriceCalculation(new Price(0.10, USD)),
            new PriceKey("Pineville", CONSTRUCTION, VisitorType.PRIVATE), new FlatRatePriceCalculation(new Price(0.15, USD)),
            new PriceKey("Pineville", GREEN, VisitorType.BUSINESS), new FlatRatePriceCalculation(new Price(0.09, USD)),
            new PriceKey("Pineville", CONSTRUCTION, VisitorType.BUSINESS), new FlatRatePriceCalculation(new Price(0.15, USD))
        );
        Map<PriceKey, TierBasedPriceCalculation> tierBasedRules = Map.of(
            new PriceKey("Oak City", CONSTRUCTION, VisitorType.BUSINESS),
            new TierBasedPriceCalculation(1000, new Price(0.21, USD), new Price(0.29, USD))
        );

        return new Context(externalVisitors, new PricingRules(flatRateRules, tierBasedRules));
    }
}
