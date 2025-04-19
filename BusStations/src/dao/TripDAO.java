package dao;

import vao.Trip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// trips.txt

public class TripDAO {

    private final String path;

    public TripDAO(String path) {
        this.path = path;
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<Trip>();
        Logger logger = Logger.getLogger(TripDAO.class.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 3) continue;

                int routeId = Integer.parseInt(tokens[0]);
                String tripId = tokens[2];

                trips.add(new Trip(tripId, routeId));
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return trips;
    }

}
