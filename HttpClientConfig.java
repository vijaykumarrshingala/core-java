package com.example.demo.config;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.ProxySelector;
import org.apache.http.impl.conn.DefaultProxySelector;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.client.HttpClient;
import org.apache.http.impl.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Builder;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient() {
        // Proxy details
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";

        // Proxy authentication
        String encodedAuth = Base64.getEncoder().encodeToString((proxyUser + ":" + proxyPassword).getBytes("UTF-8"));

        // Set up proxy with HttpClient 5
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        // Create HttpClient with the proxy setup
        HttpClient httpClient = HttpClients.custom()
                .setProxy(new InetSocketAddress(proxyHost, proxyPort))
                .addInterceptorFirst(new HttpRequestInterceptor() {
                    @Override
                    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                        request.addHeader("Proxy-Authorization", "Basic " + encodedAuth);
                    }
                })
                .build();

        return httpClient;
    }

    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
