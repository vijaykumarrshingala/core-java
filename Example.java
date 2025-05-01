public class ProxyTest {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.useSystemProxies", "true");
        var url = new java.net.URL("https://www.google.com");
        var conn = (java.net.HttpURLConnection) url.openConnection();
        conn.connect();
        System.out.println("Response: " + conn.getResponseCode());
    }
}
