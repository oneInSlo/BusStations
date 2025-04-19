package dao;

import vao.Stop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// stops.txt

public class StopDAO {

    private final String path;

    public StopDAO(String path) {
        this.path = path;
    }

    public List<Stop> getStops() {
        List<Stop> stops = new ArrayList<>();
        Logger logger = Logger.getLogger(StopDAO.class.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 3) continue;

                int stopIdTemp = Integer.parseInt(tokens[0]);
                String stopName = tokens[2];

                stops.add(new Stop(stopIdTemp, stopName));
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return stops;
    }

    public Stop getStop(int stopId) {
        for (Stop stop : getStops()) {
            if (stop.getId() == stopId)
                return stop;
        }
        return null;
    }

}
