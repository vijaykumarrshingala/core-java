import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class RestClientConfig {

    private final SslBundles sslBundles;

    public RestClientConfig(SslBundles sslBundles) {
        this.sslBundles = sslBundles;
    }

    @Bean
    public RestClient restClient() throws Exception {
        SslBundle sslBundle = sslBundles.getBundle("client");

        var sslContext = SslContextBuilder
                .forClient()
                .trustManager(sslBundle.getTrustManagerFactory())
                .keyManager(sslBundle.getKeyManagerFactory())
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(t -> t.sslContext(sslContext));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return RestClient.builder(webClient).build();
    }
}
