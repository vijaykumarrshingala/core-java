import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.config.RequestConfig;

public class ProxyWithAuthExample {

    public static void main(String[] args) throws Exception {
        // Proxy info
        String proxyHost = "your.proxy.host";     // e.g., "10.10.1.100"
        int proxyPort = 8080;
        String proxyUser = "your_proxy_user";
        String proxyPass = "your_proxy_password";

        // Target URL
        String targetUrl = "https://www.example.com";

        // Set proxy
        HttpHost proxy = new HttpHost("http", proxyHost, proxyPort);

        // Credentials provider for proxy
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(proxy),
            new UsernamePasswordCredentials(proxyUser, proxyPass.toCharArray())
        );

        // Build HTTP client
        CloseableHttpClient httpclient = HttpClients.custom()
            .setProxy(proxy)
            .setDefaultCredentialsProvider(credsProvider)
            .build();

        try {
            HttpGet httpget = new HttpGet(targetUrl);
            System.out.println("Executing request: " + httpget.getMethod() + " " + httpget.getUri());

            httpclient.execute(httpget, response -> {
                System.out.println("Response status: " + response.getCode());
                System.out.println("Response body: " + EntityUtils.toString(response.getEntity()));
                return null;
            });

        } finally {
            httpclient.close();
        }
    }
}
