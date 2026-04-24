package com.ddd_in_java.workshop.presentation;

import com.ddd_in_java.workshop.application.Context;
import com.ddd_in_java.workshop.application.PriceCalculator;
import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.infrastructure.HttpExternalVisitors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    private final String userApiBaseUrl;
    private Context context;

    public MainController(@Value("${USER_API}") String userApiBaseUrl) {
        this.userApiBaseUrl = userApiBaseUrl;
    }

    private void internalInitializeContext() {
        this.context = Context.initialize(new HttpExternalVisitors(userApiBaseUrl));
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
        ExternalVisitor visitor = context.externalVisitors
            .findById(request.person_id())
            .orElseThrow(() -> new VisitorNotFound(request.person_id()));
        String city = visitor.city();

        var fractions = request.dropped_fractions().stream()
            .map(dto -> new DroppedFraction(
                FractionType.fromString(dto.fraction_type(), city),
                new Weight(dto.amount_dropped())))
            .toList();
        var visit = new Visit(request.person_id(), request.localDate());
        var price = new PriceCalculator(context.visitHistories).calculate(fractions, visit, visitor.type());
        return ResponseEntity.ok(new PriceCalculationResponse(
            price.amount(), price.currency().toString(),
            request.visit_id(), request.person_id()));
    }

    public record StatusResponse(String status) {

    }
}
