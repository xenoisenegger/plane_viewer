package planviewer.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import planviewer.entities.Location;
import planviewer.entities.Plane;
import planviewer.service.ConfigReader;
import planviewer.service.LogWriter;
import planviewer.service.RestReader;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static planviewer.view.PlaneViewer.ALT_TEXT;

public final class PlaneFlightService {
    static String airLabsApiKey = ConfigReader.getAirLabsApiKey();
    static String openSkyApiUrl = ConfigReader.getOpenSkyApiUrl();
    static String geoApiKey = ConfigReader.getGeoApiKey();
    static String airlabsApiUrl = ConfigReader.getAirLabsApiUrl();
    static String geoapifyUrl = ConfigReader.getGeoapifyApiUrl();

    private PlaneFlightService() {
    }

    private static double calculateHaversineDistance(Location position1, Location position2) {
        double lat1 = position1.getLatitude();
        double lon1 = position1.getLongitude();
        double lat2 = position2.getLatitude();
        double lon2 = position2.getLongitude();
        double rad = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return rad * c;
    }

    public static String getNearestAirplane(Location position) {

        double minDistance = Double.MAX_VALUE;
        JSONArray states;
        String url = openSkyApiUrl + "states/all?lamin=" + (position.getLatitude() - 1) + "&lomin=" + (position.getLongitude() - 1) + "&lamax=" + (position.getLatitude() + 1) + "&lomax=" + (position.getLongitude() + 1);
        states = RestReader.getJSONObjectFromURL(url).optJSONArray("states");
        JSONArray nearestAirplane = IntStream.range(0, states.length())
                .mapToObj(states::optJSONArray)
                .min((p1, p2) -> {
                    double distance1 = calculateHaversineDistance(position, new Location(p1.optDouble(6), p1.optDouble(5)));
                    double distance2 = calculateHaversineDistance(position, new Location(p2.optDouble(6), p2.optDouble(5)));

                    return Double.compare(distance1, distance2);
                }).get();
        return nearestAirplane.optString(1).replaceAll("[^a-zA-Z0-9]", "");
    }

    public static Plane getFlightInformation(String callsign) {
        String url = airlabsApiUrl + "/flight?flight_icao=" + callsign + "&api_key=" + airLabsApiKey;
        JSONObject flightInfo = RestReader.getJSONObjectFromURL(url);
        JSONObject info;
        try {
            info = flightInfo.getJSONObject("response");
        } catch (JSONException e) {
            return new Plane(callsign, ALT_TEXT, ALT_TEXT, ALT_TEXT, ALT_TEXT);
        }
        String arrivalAirport = info.optString("arr_name", info.optString("arr_icao"));
        String departureAirport = info.optString("dep_name", info.optString("dep_icao"));
        String airline = info.optString("airline_name", info.optString("airline_icao"));
        String aircraft = info.optString("model", info.optString("aircraft_icao"));

        return new Plane(callsign, departureAirport, arrivalAirport, airline, aircraft);
    }

    public static Location getPosition(String location) throws LocationNotFoundException {
        String url = geoapifyUrl + "/geocode/search?text=" + normalize(location) + "&apiKey=" + geoApiKey;

        JSONObject locationInfo = RestReader.getJSONObjectFromURL(url);
        JSONArray features = locationInfo.optJSONArray("features");
        JSONObject feature = features.optJSONObject(0);
        if (feature == null) {
            throw new LocationNotFoundException();
        }
        String name = feature.optJSONObject("properties").optString("city", ALT_TEXT);

        JSONObject geometry = feature.optJSONObject("geometry");
        JSONArray coordinates = geometry.optJSONArray("coordinates");
        double longitude = coordinates.optDouble(0);
        double latitude = coordinates.optDouble(1);

        return new Location(latitude, longitude, name);
    }

    private static String normalize(String input) {
        String output = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(output).replaceAll("");
    }

    public static Location getIPPosition() {
        String url = geoapifyUrl + "/ipinfo?apiKey=" + geoApiKey;

        JSONObject locationInfo = RestReader.getJSONObjectFromURL(url);
        JSONObject coordinates = locationInfo.optJSONObject("location");
        double longitude = coordinates.optDouble("longitude");
        double latitude = coordinates.optDouble("latitude");
        String name = locationInfo.optJSONObject("city").optString("name", ALT_TEXT);

        return new Location(latitude, longitude, name);
    }

    public static void logPlane(Plane plane, String name) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String logEntry = dtf.format(now) + " " + name + " " + plane.toString();

        LogWriter.writeLog(logEntry);
    }
}
