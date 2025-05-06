import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;

public class HttpGetWithProxyAuthAndTrustStore {

    public static void main(String[] args) {
        try {
            // === Trust Store Configuration ===
            String trustStorePath = "path/to/your/truststore.jks";  // Or .p12 if using PKCS12
            String trustStorePassword = "changeit"; // Set your trust store password

            KeyStore trustStore = KeyStore.getInstance("JKS"); // or "PKCS12"
            try (FileInputStream fis = new FileInputStream(trustStorePath)) {
                trustStore.load(fis, trustStorePassword.toCharArray());
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            // === Proxy Configuration ===
            String proxyHost = "your-proxy-host";
            int proxyPort = 8080;
            String proxyUser = "your-username";
            String proxyPassword = "your-password";

            String encodedAuth = Base64.getEncoder().encodeToString(
                    (proxyUser + ":" + proxyPassword).getBytes(StandardCharsets.UTF_8)
            );

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // === HTTPS Request Setup ===
            URL url = new URL("https://www.google.com"); // HTTPS URL
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(proxy);

            // Apply custom SSL context
            connection.setSSLSocketFactory(sslContext.getSocketFactory());

            // Add proxy auth header
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedAuth);

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getErrorStream() != null ? connection.getErrorStream() : connection.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            )) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
