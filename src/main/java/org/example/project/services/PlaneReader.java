package org.example.project.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaneReader {
    public static JSONObject readJSON(String url){
        String line;
        StringBuilder temp = new StringBuilder();
        try {
            URL url1 = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) url1.openConnection()).getInputStream()));){
                while ((line = reader.readLine()) != null) {
                    temp.append(line);
                    return new JSONObject(temp.toString());
                }
            }
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
