package com.ddd_in_java.workshop.presentation;

import com.ddd_in_java.workshop.application.Context;
import com.ddd_in_java.workshop.application.PriceCalculator;
import com.ddd_in_java.workshop.domain.DroppedFraction;
import com.ddd_in_java.workshop.domain.ExternalVisitor;
import com.ddd_in_java.workshop.domain.FractionType;
import com.ddd_in_java.workshop.domain.VisitorNotFound;
import com.ddd_in_java.workshop.domain.Weight;
import com.ddd_in_java.workshop.infrastructure.HttpExternalVisitors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class MainController {
    private static final String DEFAULT_USER_API = "http://localhost:9000";

    private Context context;

    private void internalInitializeContext() {
        this.context = Context.initialize(new HttpExternalVisitors(resolveUserApiBaseUrl()));
    }

    private String resolveUserApiBaseUrl() {
        String configured = System.getenv("USER_API");
        if (configured == null || configured.isBlank()) {
            return DEFAULT_USER_API;
        }
        return configured;
    }

    private Context context() {
        if (context == null) {
            internalInitializeContext();
        }
        return context;
    }

    @GetMapping("/")
    public ResponseEntity<StatusResponse> home() {
        return ResponseEntity.ok(new StatusResponse("OK"));
    }

    @PostMapping("/startScenario")
    public ResponseEntity<StatusResponse> internalInit() {
        internalInitializeContext();

        return ResponseEntity.ok(new StatusResponse("OK"));
    }

    @PostMapping("/calculatePrice")
    public ResponseEntity<PriceCalculationResponse> calculatePrice(@RequestBody PriceCalculationRequest request) {
        LocalDate visitDate = LocalDate.parse(request.date());
        ExternalVisitor visitor = context().externalVisitors
            .findById(request.person_id())
            .orElseThrow(() -> new VisitorNotFound(request.person_id()));
        String city = visitor.city();

        var fractions = request.dropped_fractions().stream()
            .map(dto -> new DroppedFraction(
                FractionType.fromString(dto.fraction_type(), city),
                new Weight(dto.amount_dropped())))
            .toList();
        var price = new PriceCalculator().calculate(fractions);
        int visitNumber = context().visitHistory.visitNumberFor(request.person_id(), request.visit_id(), visitDate);
        if (visitNumber >= 3) {
            price = price.times(1.05);
        }
        context().visitHistory.record(request.person_id(), request.visit_id(), visitDate);
        return ResponseEntity.ok(new PriceCalculationResponse(
            price.amount(), price.currency().toString(),
            request.visit_id(), request.person_id()));
    }

    public record StatusResponse(String status) {

    }
}
