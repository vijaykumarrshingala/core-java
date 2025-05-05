package com.example.demo.service;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class HttpClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendPostRequestWithJson(String url, String jsonRequest) {
        // Set headers for content type and accept JSON
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        // Create HTTP entity with the headers and body
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // Send POST request and get the response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return the response body (JSON)
        return response.getBody();
    }
}
