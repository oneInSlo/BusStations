package service;

import dao.StopDAO;
import vao.ArrivalTime;
import vao.Stop;
import vao.enums.TimeFormat;

import java.time.LocalTime;
import java.util.*;

public class BusService {

    private final int stopId;
    private final int numberOfBuses;
    private final TimeFormat timeFormat;

    private final ArrivalTimeService arrivalTimeService;
    private final TripService tripService;

    public BusService(int stopId, int numberOfBuses, TimeFormat timeFormat, ArrivalTimeService arrivalTimeService, TripService tripService) {
        this.stopId = stopId;
        this.numberOfBuses = numberOfBuses;
        this.timeFormat = timeFormat;
        this.arrivalTimeService = arrivalTimeService;
        this.tripService = tripService;
    }

    public Map<Integer, List<ArrivalTime>> getBusArrivalTimes() {

        Map<Integer, List<ArrivalTime>> result = new HashMap<>();

        try {
            List<ArrivalTime> arrivalTimes = arrivalTimeService.getUpcomingArrivals(stopId);

            for (ArrivalTime arrival : arrivalTimes) {
                Integer routeId = tripService.getRouteIdForTrip(arrival.getTripId());
                if (routeId == null) continue;

                result.putIfAbsent(routeId, new ArrayList<>());
                result.get(routeId).add(arrival);
            }

            for (Map.Entry<Integer, List<ArrivalTime>> entry : result.entrySet()) {
                List<ArrivalTime> limit = entry.getValue().stream()
                        .sorted(Comparator.comparing(ArrivalTime::getArrivalTime))
                        .limit(numberOfBuses)
                        .toList();
                entry.setValue(limit);
            }
            return result;

        } catch (Exception e) {
            System.out.println("Napaka pri obdelavi casa prihoda avtobusa.");
            e.printStackTrace();
            return null;
        }
    }

    public void printBusArrivals(Map<Integer, List<ArrivalTime>> busArrivalTimes) {
        StopDAO stopDAO = new StopDAO("../gtfs/stops.txt");
        Stop stop = stopDAO.getStop(stopId);

        if (stop == null) {
            System.out.println("Neznano postajalisce: " + stopId);
            return;
        } else {
            System.out.println("Postajalisce " + stop.getName());
        }

        for (Map.Entry<Integer, List<ArrivalTime>> entry : busArrivalTimes.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            List<String> formatted = new ArrayList<>();

            for (ArrivalTime arrivalTime : entry.getValue()) {
                if (timeFormat == TimeFormat.ABSOLUTE) {
                    formatted.add(arrivalTime.getArrivalTime().toString());
                } else if (timeFormat == TimeFormat.RELATIVE) {
                    // namesto LocalTime.of(12, 00) :: LocalTime.now()
                    long secondsTemp = java.time.Duration.between(LocalTime.of(12, 00), arrivalTime.getArrivalTime()).getSeconds();

                    if (secondsTemp < 0) continue;

                    long minutes = secondsTemp / 60;
                    long seconds = secondsTemp % 60;

                    String time = (minutes > 0 ? minutes + "min" : "") + (seconds > 0 ? " " + seconds + "s" : "");
                    formatted.add(time);
                }
            }
            System.out.println(String.join(", ", formatted));
        }
    }

}
