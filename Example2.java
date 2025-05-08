import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() throws Exception {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public CloseableHttpClient httpClient() throws Exception {
        // Configure proxy
        HttpHost proxy = new HttpHost("http", "your.proxy.host", 8080);
        
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxy),
                new UsernamePasswordCredentials("proxyUsername", "proxyPassword".toCharArray())
        );

        // Configure SSL with your truststore using the builder pattern
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(
                        new java.io.File("path/to/your/truststore.jks"),
                        "truststorePassword".toCharArray()
                )
                .build();

        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(sslContext)
                        .build())
                .build();

        // Configure timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30, TimeUnit.SECONDS)
                .setConnectionRequestTimeout(30, TimeUnit.SECONDS)
                .setResponseTimeout(30, TimeUnit.SECONDS)
                .build();

        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setProxy(proxy)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }
}
