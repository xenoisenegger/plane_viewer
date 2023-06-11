package org.example.project.entitys;

import org.example.project.services.PositionReader;

public class Position {
    private double latitude;
    private double longitude;
    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(String location) {
        Position position = PositionReader.getPosition(location);
        this.latitude = position.getLatitude();
        this.longitude = position.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
