import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;
import io.netty.handler.ssl.SslContext;

import java.security.NoSuchAlgorithmException;

@Configuration
public class RestClientConfig {

    private final SslBundles sslBundles;

    public RestClientConfig(SslBundles sslBundles) {
        this.sslBundles = sslBundles;
    }

    @Bean
    public RestClient secureRestClient() throws Exception {
        // Get the SSL Bundle
        SslBundle sslBundle = sslBundles.getBundle("client");

        // Create Netty SslContext from it
        SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(sslBundle.getTrustManagerFactory())
            .keyManager(sslBundle.getKeyManagerFactory())
            .build();

        // Create Reactor Netty HttpClient with SSL context
        HttpClient httpClient = HttpClient.create()
            .secure(sslSpec -> sslSpec.sslContext(sslContext));

        // Build and return RestClient
        return RestClient.builder()
            .httpConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }
}
