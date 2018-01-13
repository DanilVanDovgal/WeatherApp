package openWeatherMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnection {

    private static final String URLPATH = "http://api.openweathermap.org/data/2.5/weather?id=703448&units=metric&appid=8a1ad16d46827679d9681ad0a9657916";

    public static String performRequest() throws IOException {
        URL url = new URL(URLPATH);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        StringBuilder sb = new StringBuilder();

        try(BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String s;
            while((s = bf.readLine()) != null) {
                sb.append(s);
            }
        } finally {
            httpURLConnection.disconnect();
        }

        return sb.toString();
    }
}
