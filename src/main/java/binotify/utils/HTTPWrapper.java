package binotify.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HTTPWrapper {
    public static Integer createJSONRequest(String url, String method, String body) {
        try {
            URLConnection con = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod(method);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setDoOutput(true);

            http.connect();

            try (OutputStream os = con.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            Integer code = http.getResponseCode();
            http.disconnect();

            return code;
        } catch (Exception e) {
            return 500;
        }
    }
}
