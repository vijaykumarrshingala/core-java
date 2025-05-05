import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ProxyRestTemplateApp {

    public static void main(String[] args) {
        RestTemplate restTemplate = createRestTemplateWithProxy();
        String response = restTemplate.getForObject("https://www.google.com", String.class);
        System.out.println(response);
    }

    public static RestTemplate createRestTemplateWithProxy() {
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(proxyHost, proxyPort),
            new UsernamePasswordCredentials(proxyUser, proxyPassword)
        );

        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        // Preemptive proxy authentication interceptor
        HttpRequestInterceptor preemptiveProxyAuth = (HttpRequest request, HttpContext context) -> {
            request.addHeader("Proxy-Authorization", basicAuthHeader(proxyUser, proxyPassword));
        };

        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .setProxy(proxy)
            .addInterceptorFirst(preemptiveProxyAuth)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    private static String basicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
