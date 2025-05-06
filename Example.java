import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.AuthSchemes;
import org.apache.hc.client5.http.auth.Credentials;
import org.apache.hc.client5.http.auth.NTCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.io.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        // Proxy config
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";
        String proxyDomain = "your-domain";
        String workstation = "your-workstation";

        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        // Updated NTCredentials using builder (non-deprecated)
        Credentials ntlmCreds = NTCredentials.Builder.create()
                .setUserName(proxyUser)
                .setPassword(proxyPassword.toCharArray())
                .setWorkstation(workstation)
                .setDomain(proxyDomain)
                .build();

        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxy, AuthSchemes.NTLM),
                ntlmCreds
        );

        // Optional preemptive header (some proxies require this)
        String auth = proxyDomain + "\\" + proxyUser + ":" + proxyPassword;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpRequestInterceptor forceProxyAuth = (HttpRequest request, HttpContext context) -> {
            request.addHeader("Proxy-Authorization", "Basic " + encoded);
        };

        CloseableHttpClient client = HttpClients.custom()
                .setProxy(proxy)
                .setDefaultCredentialsProvider(credsProvider)
                .addRequestInterceptorFirst(forceProxyAuth) // Remove if not needed
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
