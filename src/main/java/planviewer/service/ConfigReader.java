package planviewer.service;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }

            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAirLabsApiKey() {
        return properties.getProperty("AIRLABS_API_KEY");
    }

    public static String getGeoApiKey() {
        return properties.getProperty("GEO_API_KEY");
    }

    public static String getOpenSkyApiUrl() {
        return properties.getProperty("OPENSKY_API_URL");
    }

    public static String getAirLabsApiUrl() {
        return properties.getProperty("AIRLABS_API_URL");
    }

    public static String getGeoapifyApiUrl() {
        return properties.getProperty("GEOAPIFY_API_URL");
    }
}
