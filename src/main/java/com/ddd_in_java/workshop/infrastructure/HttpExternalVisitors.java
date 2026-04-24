package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.domain.ExternalVisitor;
import com.ddd_in_java.workshop.domain.ExternalVisitors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

public class HttpExternalVisitors implements ExternalVisitors {

    private record UserDto(String id, String type, String address, String city) {}

    private final RestClient restClient;

    public HttpExternalVisitors(String baseUrl) {
        this(RestClient.builder().baseUrl(normalizeBaseUrl(baseUrl)));
    }

    HttpExternalVisitors(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    private static String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("USER_API must be configured with an absolute URL");
        }

        String normalized = baseUrl.trim();
        if (!normalized.contains("://")) {
            normalized = "http://" + normalized;
        }
        return normalized;
    }

    @Override
    public Optional<ExternalVisitor> findById(String id) {
        List<UserDto> users = restClient.get()
            .uri("/api/users")
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});

        return users.stream()
            .filter(u -> u.id().equals(id))
            .findFirst()
            .map(u -> new ExternalVisitor(u.id(), u.type(), u.address(), u.city()));
    }
}
