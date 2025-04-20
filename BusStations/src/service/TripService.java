package service;

import dao.TripDAO;
import vao.Trip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripService {

    private final Map<String, Integer> tripToRoute;

    public TripService(String path) {
        TripDAO tripDAO = new TripDAO(path);
        List<Trip> trips = tripDAO.getTrips();

        tripToRoute = new HashMap<>();

        for (Trip trip : trips) {
            tripToRoute.put(trip.getTripId(), trip.getRouteId());
        }
    }

    public Integer getRouteIdForTrip(String tripId) {
        return tripToRoute.get(tripId);
    }

//   public boolean containsTrip(String tripId) {
//        return tripToRoute.containsKey(tripId);
//    }

}
