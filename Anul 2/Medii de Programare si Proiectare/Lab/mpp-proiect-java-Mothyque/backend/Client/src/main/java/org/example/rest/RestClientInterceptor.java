package org.example.rest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestClientInterceptor implements ClientHttpRequestInterceptor
{
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        System.out.println("--> STEP: Sending " + request.getMethod() + " to " + request.getURI());
        if (body.length > 0)
        {
            System.out.println("Payload: " + new String(body, StandardCharsets.UTF_8));
        }

        ClientHttpResponse response = execution.execute(request, body);

        System.out.println("<-- STEP: Received Response Status: " + response.getStatusCode());
        return response;
    }
}