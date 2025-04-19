import service.ArrivalTimeService;
import service.BusService;
import service.TripService;
import vao.ArrivalTime;
import vao.enums.TimeFormat;

import java.util.List;
import java.util.Map;

public class BusTrips {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Napaka pri vnosu!");
            System.out.println("Vnos je v formatu: <idPostaje> <steviloNaslednjihAvtobusov> <RELATIVE/ABSOLUTE>");
            return;
        }

        int stopId;
        int numberOfBuses;
        TimeFormat timeFormat;

        try {
            stopId = Integer.parseInt(args[0]);
            numberOfBuses = Integer.parseInt(args[1]);
            if (numberOfBuses <= 0) {
                throw new IllegalArgumentException("Stevilo avtobusov mora biti vecje od 0.");
            }
            timeFormat = TimeFormat.valueOf(args[2].toUpperCase());
        } catch (Exception e) {
            System.out.println("Neveljaven vnos.");
            e.printStackTrace();
            return;
        }

        BusService busService = new BusService(stopId, numberOfBuses, timeFormat, new ArrivalTimeService("../gtfs/stop_times.txt"), new TripService("../gtfs/trips.txt"));

        Map<Integer, List<ArrivalTime>> arrivals = busService.getBusArrivalTimes();
        busService.printBusArrivals(arrivals);
    }
}