package org.example.project.view;

import org.example.project.controler.Controller;
import org.example.project.entitys.Plane;
import org.example.project.entitys.Position;

public class PlaneViewer {
    public static String AIRLABSAPIKEY = "78336a52-4c40-46a5-b151-a04eec6234d1";
    public static String GEOAPIKEY = "81077ecff1c04e359abed287246a3dd0";
    public static String PLACEHOLDER = "no information found";
    public static void main(String[] args) throws Exception {

        Position position = new Position("bern");
        String callsign = Controller.getNearestAirplane(position).replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(callsign);
        Plane plane = Controller.getFlightInformation(callsign);
        System.out.println(plane.toString());
    }
}
