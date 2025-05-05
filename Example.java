import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProxyRestTemplateApp {

    public static void main(String[] args) {
        SpringApplication.run(ProxyRestTemplateApp.class, args);

        RestTemplate restTemplate = new ProxyRestTemplateApp().restTemplate();
        String response = restTemplate.getForObject("http://www.google.com", String.class);
        System.out.println(response);
    }

    @Bean
    public RestTemplate restTemplate() {
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
            new AuthScope(proxyHost, proxyPort),
            new UsernamePasswordCredentials(proxyUser, proxyPassword.toCharArray())
        );

        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credentialsProvider)
            .setProxy(proxy)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
}
