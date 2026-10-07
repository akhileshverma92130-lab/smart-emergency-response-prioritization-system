package Model;

/**
 * Emergency incident - patient/accident details
 * Priority based on severity (1=critical, 5=minor)
 */
public class Emergency implements Comparable<Emergency> {
    private int id;
    private Location location;
    private int severity;        // 1=Critical, 2=Serious, 3=Moderate, 4=Minor, 5=Low
    private String type;         // "Cardiac", "Accident", "Fire", "Trauma", etc.
    private String description;
    private long timestamp;      // When reported
    private boolean assigned;    // Ambulance assigned?

    public Emergency(int id, Location location, int severity, String type, String description) {
        this.id = id;
        this.location = location;
        this.severity = severity;
        this.type = type;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
        this.assigned = false;
    }

    // For MaxHeap - higher severity = higher priority (lower number = higher priority)
    @Override
    public int compareTo(Emergency other) {
        if (this.severity != other.severity) {
            return Integer.compare(this.severity, other.severity); // 1 comes before 2
        }
        // If same severity, earlier timestamp gets priority
        return Long.compare(this.timestamp, other.timestamp);
    }

    // Getters & Setters
    public int getId() { return id; }
    public Location getLocation() { return location; }
    public int getSeverity() { return severity; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public long getTimestamp() { return timestamp; }
    public boolean isAssigned() { return assigned; }
    public void setAssigned(boolean assigned) { this.assigned = assigned; }

    public String getSeverityLabel() {
        switch(severity) {
            case 1: return "CRITICAL";
            case 2: return "SERIOUS";
            case 3: return "MODERATE";
            case 4: return "MINOR";
            default: return "LOW";
        }
    }

    @Override
    public String toString() {
        return "Emergency #" + id + " [" + getSeverityLabel() + "] " + type +
                " at " + location + " - " + description;
    }
}