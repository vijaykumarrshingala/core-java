import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Base64;

public class ProxyWithBasicAuthManual {

    public static void main(String[] args) throws Exception {
        // Proxy details
        String proxyHost = "abpm-di99-8001";
        int proxyPort = 8085;
        String username = "amit101";
        String password = "yourPassword";

        // Encode Basic Auth manually
        String credentials = username + ":" + password;
        String encodedCreds = Base64.encode(credentials.getBytes());
        String proxyAuthHeader = "Basic " + encodedCreds;

        // Target URL
        String targetUrl = "https://httpbin.org/ip";

        // Create proxy host
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);

        // Build request config with proxy
        RequestConfig config = RequestConfig.custom()
            .setProxy(proxy)
            .build();

        // Build client
        try (CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build()) {

            HttpGet request = new HttpGet(targetUrl);

            // Add Proxy-Authorization header manually
            request.addHeader("Proxy-Authorization", proxyAuthHeader);

            String response = client.execute(request, httpResponse ->
                EntityUtils.toString(httpResponse.getEntity())
            );

            System.out.println("Response:\n" + response);
        }
    }
}
