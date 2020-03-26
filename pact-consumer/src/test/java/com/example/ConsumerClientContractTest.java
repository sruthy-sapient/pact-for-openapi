package com.example;

import static junit.framework.TestCase.assertEquals;

import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

public class ConsumerClientContractTest {

    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("Foo_Provider", this);

    @Pact(consumer = "Foo_Consumer")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("a request for Foos")
                .path("/posts/1")
                .method("GET")

                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("{\n" +
                        "  \"userId\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                        "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                        "}").toPact();
    }

    @Test
    @PactVerification()
    public void runTest() {
		assertEquals(new RestTemplate().getForEntity(rule.getUrl() + "/posts/1", String.class).getStatusCode(),
				HttpStatus.valueOf(200));
    }
}
