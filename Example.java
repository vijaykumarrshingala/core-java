import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWithProxyAuth {

    public static RestTemplate createRestTemplateWithProxy() {
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";

        // 1. Set credentials for proxy
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(proxyHost, proxyPort),
            new UsernamePasswordCredentials(proxyUser, proxyPassword)
        );

        // 2. Create HTTP client with proxy and credentials
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .setProxy(proxy)
            .build();

        // 3. Create request factory with that HTTP client
        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

        // 4. Create RestTemplate
        return new RestTemplate(requestFactory);
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = createRestTemplateWithProxy();
        String result = restTemplate.getForObject("http://www.google.com", String.class);
        System.out.println(result);
    }
}
