import java.net.*;

public class WindowsProxySettings {

    public static void main(String[] args) throws Exception {
        // Ask Java to use system proxies
        System.setProperty("java.net.useSystemProxies", "true");

        URI uri = new URI("http://www.google.com"); // you can test with https as well

        System.out.println("Detecting proxies for: " + uri);

        ProxySelector selector = ProxySelector.getDefault();
        for (Proxy proxy : selector.select(uri)) {
            System.out.println("Proxy Type: " + proxy.type());

            SocketAddress address = proxy.address();
            if (address != null) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                System.out.println("Proxy Host: " + inetSocketAddress.getHostName());
                System.out.println("Proxy Port: " + inetSocketAddress.getPort());
            } else {
                System.out.println("Direct connection (no proxy)");
            }
        }
    }
}
