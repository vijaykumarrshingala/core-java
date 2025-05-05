import java.io.*;
import java.net.*;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;

public class HttpPostWithJson {

    public static void main(String[] args) {
        // Proxy and authentication details (if required)
        String proxyHost = "your-proxy-host";
        int proxyPort = 8080;
        String proxyUser = "your-username";
        String proxyPassword = "your-password";

        // Target URL
        String targetUrl = "http://your-api-endpoint.com/api";

        // JSON data to be sent in the body of the POST request
        String jsonRequest = "{\"name\": \"John Doe\", \"email\": \"johndoe@example.com\"}";

        // Call method to send request with proxy authentication
        sendPostRequestWithJson(proxyHost, proxyPort, proxyUser, proxyPassword, targetUrl, jsonRequest);
    }

    private static void sendPostRequestWithJson(String proxyHost, int proxyPort, String proxyUser, String proxyPassword, String targetUrl, String jsonRequest) {
        try {
            // Encode the username and password for Proxy Authentication (Basic Auth)
            String encodedAuth = Base64.getEncoder().encodeToString(
                    (proxyUser + ":" + proxyPassword).getBytes("UTF-8")
            );

            // Create Proxy object
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // Open a connection to the target URL using the proxy
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            // Set request headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedAuth);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Enable output stream to send data in the body of the POST request
            connection.setDoOutput(true);

            // Write JSON request body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Handle the response (expecting JSON)
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            // Print the response (assuming it's in JSON format)
            System.out.println("Response Body: " + response.toString());

        } catch (IOException e) {
            System.err.println("An error occurred during the HTTP request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Request completed.");
        }
    }
}
