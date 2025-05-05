import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Base64;

public class HttpGetWithProxyAuth {

    public static void main(String[] args) {
        try {
            // Proxy details
            String proxyHost = "your-proxy-host";
            int proxyPort = 8080;

            // Proxy credentials
            String proxyUser = "your-username";
            String proxyPassword = "your-password";

            // Encode username:password in Base64
            String encodedAuth = Base64.getEncoder().encodeToString(
                    (proxyUser + ":" + proxyPassword).getBytes("UTF-8")
            );

            // Create proxy
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // Target URL
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            // Add Proxy-Authorization header
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedAuth);

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Read response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getErrorStream() != null ? connection.getErrorStream() : connection.getInputStream()
                    )
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
