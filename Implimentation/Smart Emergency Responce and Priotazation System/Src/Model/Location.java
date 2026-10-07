package Model;

/**
 * Simple location class - GPS coordinates
 * Used for hospitals, emergencies, ambulances
 */
public class Location {
    private double latitude;
    private double longitude;
    private String name;  // landmark name

    public Location(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    // Distance calculation - haversine formula (approx)
    public double distanceTo(Location other) {
        double R = 6371; // Earth radius in km
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    // Getters
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }
}