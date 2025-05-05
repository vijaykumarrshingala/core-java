import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.*;
import java.util.Base64;

public class HttpGetWithProxyAuth {

    public static void main(String[] args) {
        // Proxy details (you can change these values accordingly)
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        
        // Proxy credentials
        String proxyUser = "your-username";
        String proxyPassword = "your-password";
        
        // Target URL
        String targetUrl = "http://www.google.com";  // Change this to the desired URL

        // Call the method to send request with proxy authentication
        sendRequestWithProxyAuth(proxyHost, proxyPort, proxyUser, proxyPassword, targetUrl);
    }

    private static void sendRequestWithProxyAuth(String proxyHost, int proxyPort, String proxyUser, String proxyPassword, String targetUrl) {
        try {
            // Encode the username and password for Proxy Authentication (Basic Auth)
            String encodedAuth = Base64.getEncoder().encodeToString(
                    (proxyUser + ":" + proxyPassword).getBytes("UTF-8")
            );

            // Create a Proxy object
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // Open a connection to the target URL using the proxy
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            // Add Proxy-Authorization header
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedAuth);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);  // Timeout for connecting
            connection.setReadTimeout(5000);     // Timeout for reading the response

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Using try-with-resources for auto-closing BufferedReader
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300
                                    ? connection.getInputStream()    // Success response body
                                    : connection.getErrorStream()    // Error response body
                    )
            )) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);  // Print response line by line
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred during the HTTP request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Any cleanup can be added here if necessary
            System.out.println("Request completed.");
        }
    }
}
