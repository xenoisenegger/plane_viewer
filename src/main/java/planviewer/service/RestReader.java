package planviewer.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class RestReader {
    private RestReader() {
    }

    public static JSONObject getJSONObjectFromURL(String restURL) {
        StringBuilder temp = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) new URL(restURL).openConnection()).getInputStream()));) {
            temp.append(reader.readLine());
            return new JSONObject(temp.toString());
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

