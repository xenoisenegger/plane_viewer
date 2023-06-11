package org.example.project.services;

import org.example.project.entitys.Position;
import org.example.project.view.PlaneViewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

public class PositionReader {
    public static Position getPosition(String location){
        try {
            String url = "https://api.geoapify.com/v1/geocode/search?text=" + URLEncoder.encode(location, "UTF-8") + "&apiKey=" + PlaneViewer.GEOAPIKEY;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray features = jsonObject.getJSONArray("features");
            JSONObject feature = features.getJSONObject(0);
            JSONObject geometry = feature.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            double longitude = coordinates.getDouble(0);
            double latitude = coordinates.getDouble(1);

            return new Position(latitude, longitude);
        }catch (IOException | JSONException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}