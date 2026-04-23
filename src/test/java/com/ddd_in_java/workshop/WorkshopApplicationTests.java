package com.ddd_in_java.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class WorkshopApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void calculatePriceReturnsExpectedResponseBody() throws Exception {
		String requestBody = """
			{
			  "date": "2023-07-23",
			  "dropped_fractions": [
			    {
			      "amount_dropped": 15,
			      "fraction_type": "Green waste"
			    },
			    {
			      "amount_dropped": 39,
			      "fraction_type": "Construction waste"
			    }
			  ],
			  "person_id": "Bald Eagle",
			  "visit_id": "1"
			}
			""";
		String expectedResponseBody = """
			{
			  "price_amount": 7.35,
			  "price_currency": "USD",
			  "visit_id": "1",
			  "person_id": "Bald Eagle"
			}
			""";

		mockMvc.perform(post("/calculatePrice")
				.contentType(APPLICATION_JSON)
				.content(requestBody))
			.andExpect(content().json(expectedResponseBody));
	}

}
