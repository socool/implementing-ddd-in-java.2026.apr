package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.domain.visitor.ExternalVisitor;
import com.ddd_in_java.workshop.domain.visitor.ExternalVisitors;
import com.ddd_in_java.workshop.domain.visitor.Visitor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

public class HttpExternalVisitors implements ExternalVisitors {

    private record UserDto(String id, String type, String address, String city, String email) {}

    private final RestClient restClient;

    public HttpExternalVisitors(String baseUrl) {
        this(RestClient.builder().baseUrl(baseUrl));
    }

    HttpExternalVisitors(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Override
    public Optional<Visitor> findById(String id) {
        List<UserDto> users = restClient.get()
            .uri("/api/users")
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});

        return users.stream()
            .filter(u -> u.id().equals(id))
            .findFirst()
            .map(u -> new ExternalVisitor(u.id(), u.type(), u.address(), u.city(), u.email()))
            .map(Visitor::FromExternalVisitor);
    }
}
