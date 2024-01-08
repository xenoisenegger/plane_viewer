package planviewer.controller;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException() {
        super("Location not Found!");
    }
}
