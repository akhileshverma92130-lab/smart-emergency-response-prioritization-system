package Model;
/**
 * Ambulance vehicle - equipment, status, current location
 */
public class Ambulance {
    private int id;
    private String vehicleNumber;
    private Location currentLocation;
    private boolean available;
    private String[] equipment;     // "Defibrillator", "Ventilator", "Stretcher", etc.
    private int maxCapacity;        // How many patients
    private int currentLoad;
    private Emergency assignedEmergency;

    public Ambulance(int id, String vehicleNumber, Location location,
                     String[] equipment, int maxCapacity) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.currentLocation = location;
        this.equipment = equipment;
        this.maxCapacity = maxCapacity;
        this.currentLoad = 0;
        this.available = true;
        this.assignedEmergency = null;
    }

    public boolean hasEquipment(String required) {
        for (String eq : equipment) {
            if (eq.equalsIgnoreCase(required)) return true;
        }
        return false;
    }

    public boolean assign(Emergency e) {
        if (available && currentLoad < maxCapacity) {
            this.assignedEmergency = e;
            this.available = false;
            this.currentLoad++;
            return true;
        }
        return false;
    }

    public void completeMission() {
        this.assignedEmergency = null;
        this.available = true;
        this.currentLoad = Math.max(0, currentLoad - 1);
    }

    public void updateLocation(Location newLoc) {
        this.currentLocation = newLoc;
    }

    // Getters
    public int getId() { return id; }
    public String getVehicleNumber() { return vehicleNumber; }
    public Location getCurrentLocation() { return currentLocation; }
    public boolean isAvailable() { return available; }
    public String[] getEquipment() { return equipment; }
    public Emergency getAssignedEmergency() { return assignedEmergency; }

    @Override
    public String toString() {
        return "Ambulance " + vehicleNumber + " [" + (available ? "AVAILABLE" : "BUSY") +
                "] at " + currentLocation + " - Load: " + currentLoad + "/" + maxCapacity;
    }
}