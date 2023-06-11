package org.example.project.entitys;

import org.example.project.view.PlaneViewer;

public class Plane {
    private String callSign;
    private String arrivalAirport;
    private String departureAirport;
    private String airline;
    private String aircraft;
    public Plane(String callSign, String destination, String source, String airline, String modell) {
            this.callSign = callSign;
            this.arrivalAirport = destination;
            this.departureAirport = source;
            this.airline = airline;
            this.aircraft = modell;
    }
    public String getCallSign() {
        return callSign;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getAirline() {
        return airline;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public String toString() {
        return "[" + callSign + "," + arrivalAirport + "," + departureAirport + "," + airline + "," + aircraft + "]";
    }
}
