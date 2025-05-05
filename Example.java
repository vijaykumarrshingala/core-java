import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class HttpGetWithExplicitProxy {

    public static void main(String[] args) {
        try {
            // Define proxy
            String proxyHost = "your-proxy-host";
            int proxyPort = 8080; // your proxy port

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // Define target URL
            URL url = new URL("http://www.google.com");

            // Open connection using explicit proxy
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Optional: If your proxy requires basic authentication
            // String encoded = Base64.getEncoder().encodeToString("username:password".getBytes());
            // connection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);

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
