package planviewer.view;

import guilib.*;
import io.github.humbleui.types.Point;
import planviewer.controller.LocationNotFoundException;
import planviewer.controller.PlaneFlightService;
import planviewer.entities.Location;
import planviewer.entities.Plane;

public class PlaneViewer extends Application {
    public static final String ALT_TEXT = "unknown";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 270;

    private Label callsignLabel;
    private Label airlineLabel;
    private Label aircraftLabel;
    private Label arrivalAirportLabel;
    private Label departureAirportLabel;
    private Label currentLocationLabel;
    private Button searchButton;
    private Button ipLocationButton;
    private TextField locationSearchBox;
    private Checkbox logCheckbox;
    private Image positionPin;


    public static void main(String[] args) {
        new PlaneViewer().start("Plane Viewer", WIDTH, HEIGHT);
    }

    @Override
    protected Group createContent() {
        Image backgroundImage = new Image(0, 20, 500, 250, PlaneViewer.class.getClassLoader().getResource("img/background.png").getPath());

        positionPin = new Image(236, 124, 26, 26, PlaneViewer.class.getClassLoader().getResource("img/position-pin.png").getPath());
        positionPin.setAnimated(true);

        callsignLabel = new Label(210, 70, 80, ALT_TEXT);
        callsignLabel.setTextAlignment(TextAlignment.CENTER);

        airlineLabel = new Label(125, 10, 250, ALT_TEXT);
        airlineLabel.setTextAlignment(TextAlignment.CENTER);

        aircraftLabel = new Label(125, 40, 250, ALT_TEXT);
        aircraftLabel.setTextAlignment(TextAlignment.CENTER);

        arrivalAirportLabel = new Label(415, 190, 80, ALT_TEXT);
        arrivalAirportLabel.setTextAlignment(TextAlignment.RIGHT);

        departureAirportLabel = new Label(5, 190, 80, ALT_TEXT);
        departureAirportLabel.setTextAlignment(TextAlignment.LEFT);

        currentLocationLabel = new Label(125, 150, 250, ALT_TEXT);
        currentLocationLabel.setTextAlignment(TextAlignment.CENTER);

        locationSearchBox = new TextField(5, 215, 240);
        locationSearchBox.setHeight(50);

        logCheckbox = new Checkbox(new Point(440, 10), "Log");
        logCheckbox.setChecked(true);

        searchButton = new Button(256, 215, "Search");
        searchButton.setHeight(50);
        searchButton.setWidth(115);
        ipLocationButton = new Button(380, 215, "IP Location");
        ipLocationButton.setHeight(50);
        ipLocationButton.setWidth(115);

        setupEventHandlers();

        return new Group(backgroundImage, positionPin, callsignLabel, airlineLabel, aircraftLabel, arrivalAirportLabel, departureAirportLabel, currentLocationLabel, locationSearchBox, searchButton, ipLocationButton, logCheckbox);
    }

    public void updatePlane(Plane plane) {
        callsignLabel.setText(plane.getCallSign());
        airlineLabel.setText(plane.getAirline());
        aircraftLabel.setText(plane.getAircraft());
        arrivalAirportLabel.setText(plane.getArrivalAirport());
        departureAirportLabel.setText(plane.getDepartureAirport());
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(() -> {
            if (locationSearchBox.getText().length() > 0) {
                Location location = null;
                try {
                    location = PlaneFlightService.getPosition(locationSearchBox.getText());
                    String callsign = PlaneFlightService.getNearestAirplane(location);
                    Plane plane = PlaneFlightService.getFlightInformation(callsign);
                    currentLocationLabel.setText(location.getName());
                    updatePlane(plane);
                    if (logCheckbox.isChecked()) {
                        PlaneFlightService.logPlane(plane, location.getName());
                    }
                } catch (LocationNotFoundException e) {
                    currentLocationLabel.setText(e.getMessage());
                    locationSearchBox.setText("");
                }
            }
        });

        ipLocationButton.setOnAction(() -> {
            Location location = PlaneFlightService.getIPPosition();
            String callsign = PlaneFlightService.getNearestAirplane(location);
            Plane plane = PlaneFlightService.getFlightInformation(callsign);
            currentLocationLabel.setText(location.getName());
            updatePlane(plane);
            if (logCheckbox.isChecked()) {
                PlaneFlightService.logPlane(plane, location.getName());
            }
        });

        locationSearchBox.setOnTextChanged(() -> {
        });
    }
}
