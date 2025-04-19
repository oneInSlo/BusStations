package service;

import dao.ArrivalTimeDAO;
import vao.ArrivalTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ArrivalTimeService {

    private final ArrivalTimeDAO arrivalTimeDAO;

    public ArrivalTimeService(String path) {
        this.arrivalTimeDAO = new ArrivalTimeDAO(path);
    }

    public List<ArrivalTime> getUpcomingArrivals(int stopId) {
        List<ArrivalTime> allArrivals = arrivalTimeDAO.getArrivalTimesByStopId(stopId);
        List<ArrivalTime> filteredArrivals = new ArrayList<>();

        for (ArrivalTime arrivalTime : allArrivals) {
            // namesto LocalTime.of(12, 00) :: getCurrentTime()
            if (!arrivalTime.getArrivalTime().isBefore(LocalTime.of(12, 00)) && arrivalTime.getArrivalTime().isBefore(LocalTime.of(12, 00).plusHours(2))) {
                filteredArrivals.add(arrivalTime);
            }
        }
        return filteredArrivals;
    }

//    public LocalTime getCurrentTime() {
//        return LocalTime.now();
//    }
}

