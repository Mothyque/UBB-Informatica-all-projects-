package org.example.rest;

import org.example.domain.Match;
import org.springframework.web.client.RestClient;

public class JavaClientTest {
    private final RestClient restClient;

    public JavaClientTest()
    {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080/api/matches")
                .requestInterceptor(new RestClientInterceptor())
                .build();
    }

    public void runTests()
    {
        System.out.println("Starting Java RestClient Tests...\n");

        Match newMatch = new Match("JavaTeamA", "JavaTeamB", "Test", 10.0, 100, 100);
        Integer id = restClient.post()
                .body(newMatch)
                .retrieve()
                .body(Integer.class);
        System.out.println("Match created with ID: " + id);

        Match found = restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(Match.class);
        System.out.println("Found match: " + found);

        Match[] all = restClient.get()
                .retrieve()
                .body(Match[].class);
        System.out.println("Total matches in DB: " + (all != null ? all.length : 0));

        Match[] filtered = restClient.get()
                .uri("?matchType=Test")
                .retrieve()
                .body(Match[].class);
        System.out.println("Filtered matches count: " + (filtered != null ? filtered.length : 0));

        if (found != null)
        {
            found.setTicketPrice(99.99);
            restClient.put()
                    .uri("/{id}", id)
                    .body(found)
                    .retrieve()
                    .toBodilessEntity();
            System.out.println("Match modified.");
        }

        restClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
        System.out.println("Match deleted.");
    }

    public static void main(String[] args)
    {
        new JavaClientTest().runTests();
    }
}