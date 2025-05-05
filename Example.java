import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class HttpGetUsingSystemProxy {

    public static void main(String[] args) {
        try {
            // Enable use of OS-level proxy settings
            System.setProperty("java.net.useSystemProxies", "true");

            URI uri = new URI("http://www.google.com");

            System.out.println("Detecting proxy for: " + uri);
            ProxySelector selector = ProxySelector.getDefault();
            Proxy proxy = selector.select(uri).get(0);

            // Show proxy info
            System.out.println("Proxy Type: " + proxy.type());
            if (proxy.address() != null) {
                InetSocketAddress addr = (InetSocketAddress) proxy.address();
                System.out.println("Using proxy: " + addr.getHostName() + ":" + addr.getPort());
            } else {
                System.out.println("Direct connection (no proxy)");
            }

            // Open connection using the detected proxy
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection(proxy);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // optional
            connection.setReadTimeout(5000);    // optional

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append('\n');
            }

            in.close();
            connection.disconnect();
