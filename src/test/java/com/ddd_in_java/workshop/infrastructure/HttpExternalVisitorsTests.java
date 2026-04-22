package com.ddd_in_java.workshop.infrastructure;

import com.ddd_in_java.workshop.domain.PrivateVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class HttpExternalVisitorsTests {

    private MockRestServiceServer mockServer;
    private HttpExternalVisitors visitors;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://test");
        mockServer = MockRestServiceServer.bindTo(builder).build();
        visitors = new HttpExternalVisitors(builder);
    }

    @Test
    void findsVisitorByIdWhenPresent() {
        mockServer.expect(requestTo("http://test/api/users"))
                .andRespond(withSuccess("""
                        [
                          {"id": "42", "type": "private", "address": "1 Main St", "city": "Oak City"},
                          {"id": "99", "type": "business", "address": "2 Elm St", "city": "Maple Town"}
                        ]
                        """, MediaType.APPLICATION_JSON));

        var visitor = visitors.findById("42");

        assertEquals(Optional.of(new PrivateVisitor("42", "Oak City")), visitor);
    }

}
