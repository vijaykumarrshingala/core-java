package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        logRequestDetails(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        logger.info("Request URI      : {}", request.getURI());
        logger.info("Request Method   : {}", request.getMethod());
        logger.info("Request Headers  : {}", request.getHeaders());
        logger.info("Request Body     : {}", new String(body));
    }

    private void logResponseDetails(ClientHttpResponse response) throws IOException {
        logger.info("Response Status  : {}", response.getStatusCode());
        logger.info("Response Headers : {}", response.getHeaders());
        // Optionally read response body here — careful: only once!
    }
}
