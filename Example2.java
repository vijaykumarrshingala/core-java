import org.apache.hc.client5.http.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.List;

@Configuration
public class RestTemplateProxyWithTrustStoreConfig {

    @Bean
    public RestTemplateCustomizer proxyWithTrustStoreCustomizer() {
        return restTemplate -> {
            try {
                // Proxy & truststore config
                String proxyHost = "your-proxy-host";
                int proxyPort = 8080;
                String bearerToken = "your-bearer-token";
                String trustStorePath = "/path/to/truststore.jks";
                String trustStorePassword = "changeit";

                // Load JKS trust store
                KeyStore trustStore = KeyStore.getInstance("JKS");
                try (FileInputStream fis = new FileInputStream(trustStorePath)) {
                    trustStore.load(fis, trustStorePassword.toCharArray());
                }

                SSLContext sslContext = SSLContexts.custom()
                        .loadTrustMaterial(trustStore, null)
                        .build();

                // Create SSL socket factory
                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

                // Connection manager with SSL support
                PoolingHttpClientConnectionManager connectionManager =
                        new PoolingHttpClientConnectionManager();
                connectionManager.setDefaultSocketConfig(sslSocketFactory.getSocketConfig());
                connectionManager.setConnectionConfigResolver(sslSocketFactory);

                HttpHost proxy = new HttpHost(proxyHost, proxyPort);

                RequestConfig requestConfig = RequestConfig.custom()
                        .setProxy(proxy)
                        .setConnectionRequestTimeout(Timeout.ofSeconds(30))
                        .setConnectTimeout(Timeout.ofSeconds(30))
                        .build();

                CloseableHttpClient httpClient = HttpClients.custom()
                        .setConnectionManager(connectionManager)
                        .setDefaultRequestConfig(requestConfig)
                        .setDefaultHeaders(List.of(
                                new BasicHeader("Proxy-Authorization", "Bearer " + bearerToken)
                        ))
                        .build();

                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

            } catch (Exception e) {
                throw new RuntimeException("Failed to configure RestTemplate with proxy and truststore", e);
            }
        };
    }
}
