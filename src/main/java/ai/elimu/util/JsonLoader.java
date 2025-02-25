package ai.elimu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonLoader {

    public static String loadJson(String urlValue) {
        log.info("loadJson");

        log.info("Downloading from " + urlValue);

        String jsonResponse = null;

        try {
            URL url = new URL(urlValue);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            log.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (jsonResponse == null) {
                    jsonResponse = "";
                }
                jsonResponse += line;
            }
        } catch (MalformedURLException e) {
            log.error("MalformedURLException", e);
        } catch (ProtocolException e) {
            log.error("ProtocolException", e);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return jsonResponse;
    }
}
