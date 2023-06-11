package org.example.project.controler;

import org.example.project.entitys.Plane;
import org.example.project.entitys.Position;
import org.example.project.services.PlaneReader;
import org.example.project.view.PlaneViewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Controller {
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static String getNearestAirplane(Position position) throws JSONException {
        JSONArray nearestAirplane = null;
        double minDistance = Double.MAX_VALUE;
        JSONArray states = null;
        String url = "https://opensky-network.org/api/states/all?lamin=" + (position.getLatitude() - 1) + "&lomin=" + (position.getLongitude() - 1) + "&lamax=" + (position.getLatitude() + 1) + "&lomax=" + (position.getLongitude() + 1);
        try {
            states = PlaneReader.readJSON(url).getJSONArray("states");
        }catch (JSONException e){
            e.printStackTrace();
        }
        for (int i = 0; i < states.length(); i++) {
            JSONArray state = states.getJSONArray(i);
            double distance = calculateDistance(position.getLatitude(), position.getLongitude(), state.getDouble(6), state.getDouble(5));

            if (distance < minDistance) {
                minDistance = distance;
                nearestAirplane = states.getJSONArray(i);
            }
        }
        return nearestAirplane.getString(1);
    }

    public static Plane getFlightInformation(String callsign){
        Plane temp = new Plane(callsign, PlaneViewer.PLACEHOLDER, PlaneViewer.PLACEHOLDER, PlaneViewer.PLACEHOLDER, PlaneViewer.PLACEHOLDER);
        JSONObject info = null;
        String url = "https://airlabs.co/api/v9/flight?flight_icao=" + callsign + "&api_key=" + PlaneViewer.AIRLABSAPIKEY;
        JSONObject flightInfo = PlaneReader.readJSON(url);
        try {
            info = flightInfo.getJSONObject("response");
        } catch (JSONException e) {
            return temp;
        }
        try {
            temp.setArrivalAirport(info.getString("arr_icao"));
        }catch (JSONException e){
            return temp;
        }
        try {
            temp.setDepartureAirport(info.getString("dep_icao"));
        }catch (JSONException e){
            return temp;
        }
        try {
            temp.setAirline(info.getString("airline_icao"));
        }catch (JSONException e){
            return temp;
        }
        try {
            temp.setAircraft(info.getString("aircraft_icao"));
        }catch (JSONException e){
            return temp;
        }
        return temp;
    }
}
