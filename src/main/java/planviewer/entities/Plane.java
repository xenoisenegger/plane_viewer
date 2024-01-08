package planviewer.entities;

public class Plane {
    private final String callSign;
    private final String arrivalAirport;
    private final String departureAirport;
    private final String airline;
    private final String aircraft;

    public Plane(String callSign, String destinationAirport, String arrivalAirport, String airline, String modell) {
        this.callSign = callSign;
        this.arrivalAirport = arrivalAirport;
        this.departureAirport = destinationAirport;
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

    @Override
    public String toString() {
        return "[" + callSign + "," + arrivalAirport + "," + departureAirport + "," + airline + "," + aircraft + "]";
    }
}
