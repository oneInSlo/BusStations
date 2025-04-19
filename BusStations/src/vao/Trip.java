package vao;

public class Trip {

    private String tripId;
    private int routeId;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Trip(String tripId, int routeId) {
        this.tripId = tripId;
        this.routeId = routeId;
    }
}
