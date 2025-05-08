import org.apache.hc.client5.http.classic.CloseableHttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.KeyStore;
import java.util.List;

@Configuration
public class RestTemplateProxyWithTrustStoreConfig {

    @Bean
    public RestTemplateCustomizer proxyWithTrustStoreCustomizer() {
        return restTemplate -> {
            try {
                // === Configuration ===
                String proxyHost = "your-proxy-host";
                int proxyPort = 8080;
                String bearerToken = "your-bearer-token";
                String trustStorePath = "/path/to/truststore.jks";
                String trustStorePassword = "changeit";

                // === Load JKS TrustStore ===
                KeyStore trustStore = KeyStore.getInstance("JKS");
                trustStore.load(new java.io.FileInputStream(new File(trustStorePath)),
                        trustStorePassword.toCharArray());

                SSLContext sslContext = SSLContextBuilder.create()
                        .loadTrustMaterial(trustStore, null)
                        .build();

                // === Proxy Configuration ===
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxy)
                        .build();

                CloseableHttpClient httpClient = HttpClients.custom()
                        .setDefaultRequestConfig(config)
                        .setDefaultHeaders(List.of(
                                new BasicHeader("Proxy-Authorization", "Bearer " + bearerToken)
                        ))
                        .setSSLContext(sslContext)
                        .build();

                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

            } catch (Exception e) {
                throw new RuntimeException("Failed to configure RestTemplate with proxy and truststore", e);
            }
        };
    }
}
