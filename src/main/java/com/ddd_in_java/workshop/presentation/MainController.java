package com.ddd_in_java.workshop.presentation;

import com.ddd_in_java.workshop.application.Context;
import com.ddd_in_java.workshop.application.PriceCalculator;
import com.ddd_in_java.workshop.application.VisitRequest;
import com.ddd_in_java.workshop.domain.*;
import com.ddd_in_java.workshop.infrastructure.HttpExternalVisitors;
import com.ddd_in_java.workshop.infrastructure.HttpInvoiceSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    private Context context;

    private void internalInitializeContext() {
        this.context = Context.initialize(
            new HttpExternalVisitors(System.getenv("USER_API")),
            new HttpInvoiceSender(System.getenv("INVOICE_API"))
        );
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
        var fractions = request.dropped_fractions().stream()
            .map(dto -> new DroppedFraction(
                FractionType.fromString(dto.fraction_type()),
                new Weight(dto.amount_dropped())))
            .toList();
        var visitRequest = new VisitRequest(request.person_id(), fractions, request.localDate());
        var price = new PriceCalculator(context.visitHistories, context.priceCalculators, context.externalVisitors, context.messageBus).calculate(visitRequest);
        return ResponseEntity.ok(new PriceCalculationResponse(
            price.amount(), price.currency().toString(),
            request.visit_id(), request.person_id()));
    }

    public record StatusResponse(String status) {

    }
}
