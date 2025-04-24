import org.apache.hc.client5.http.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.ConnectionSocketFactory;
import org.apache.hc.core5.http.io.support.PlainConnectionSocketFactory;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateSslConfig {

    @Bean
    public RestTemplate restTemplate(SslBundles sslBundles) {
        SslBundle sslBundle = sslBundles.getBundle("mybundle");

        // Create SSLConnectionSocketFactory using the SSLContext from SslBundle
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslBundle.createSslContext());

        // Register the SSLConnectionSocketFactory and PlainConnectionSocketFactory
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslSocketFactory)
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();

        // Create the connection manager with the registry
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // Optionally, configure socket timeout
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(30))
                .build();
        connectionManager.setDefaultSocketConfig(socketConfig);

        // Build the HttpClient with the custom connection manager
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // Create RestTemplate with the custom HttpClient
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
