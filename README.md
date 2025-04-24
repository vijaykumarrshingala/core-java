<dependencies>
    <dependency>
        <groupId>org.apache.httpcomponents.client5</groupId>
        <artifactId>httpclient5</artifactId>
        <version>5.2.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.httpcomponents.core5</groupId>
        <artifactId>httpcore5</artifactId>
        <version>5.2.1</version>
    </dependency>
</dependencies>











spring:
  ssl:
    bundles:
      mybundle:
        key:
          store: classpath:keystore/my-keystore.jks
          password: your_password
          type: JKS
        trust:
          store: classpath:keystore/my-keystore.jks
          password: your_password
          type: JKS





import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(SslBundles sslBundles) {
        SslBundle sslBundle = sslBundles.getBundle("mybundle");
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(
                org.apache.hc.client5.http.impl.classic.HttpClients.custom()
                        .setSSLContext(sslBundle.createSslContext())
                        .build());
        return new RestTemplate(requestFactory);
    }
}
