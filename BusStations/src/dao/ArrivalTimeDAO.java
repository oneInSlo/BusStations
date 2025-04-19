package dao;

import vao.ArrivalTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// stop_times.txt

public class ArrivalTimeDAO {

    private final String path;
    private static final Logger logger = Logger.getLogger(ArrivalTimeDAO.class.getName());

    public ArrivalTimeDAO(String path) {
        this.path = path;
    }

    public List<ArrivalTime> getArrivalTimesByStopId(int stopId) {
        List<ArrivalTime> arrivalTimes = new ArrayList<ArrivalTime>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 4) continue;

                String tripId = tokens[0];
                String arrivalTime = tokens[1];
                String currentStopId = tokens[3];

                if (!currentStopId.equals(String.valueOf(stopId))) continue;

                LocalTime arrivalTimeTemp = timeParser(arrivalTime);

                if (arrivalTimeTemp != null)
                    arrivalTimes.add(new ArrivalTime(tripId, stopId, arrivalTimeTemp));
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return arrivalTimes;
    }

    private LocalTime timeParser(String timeString) {
        try {
            String[] tokens = timeString.split(":");
            int hour = Integer.parseInt(tokens[0]) % 24;
            int minute = Integer.parseInt(tokens[1]);
            int second = Integer.parseInt(tokens[2]);
            return LocalTime.of(hour, minute, second);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

}
