package Model;

/**
 * Hospital info - capacity, specialties, current load
 */
public class Hospital {
    private int id;
    private String name;
    private Location location;
    private int totalBeds;
    private int availableBeds;
    private String[] specialties;  // e.g., "Cardiology", "Trauma", "Burn"
    private boolean hasICU;
    private boolean hasTraumaCenter;

    public Hospital(int id, String name, Location location, int totalBeds,
                    String[] specialties, boolean hasICU, boolean hasTraumaCenter) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.totalBeds = totalBeds;
        this.availableBeds = totalBeds;
        this.specialties = specialties;
        this.hasICU = hasICU;
        this.hasTraumaCenter = hasTraumaCenter;
    }

    // Check if hospital can handle this emergency type
    public boolean canHandle(String emergencyType) {
        for (String spec : specialties) {
            if (spec.equalsIgnoreCase(emergencyType)) return true;
        }
        // General hospitals can handle minor cases
        return emergencyType.equalsIgnoreCase("General") || emergencyType.equalsIgnoreCase("Minor");
    }

    public boolean admitPatient() {
        if (availableBeds > 0) {
            availableBeds--;
            return true;
        }
        return false;
    }

    public void dischargePatient() {
        if (availableBeds < totalBeds) availableBeds++;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Location getLocation() { return location; }
    public int getAvailableBeds() { return availableBeds; }
    public int getTotalBeds() { return totalBeds; }
    public boolean hasICU() { return hasICU; }
    public boolean hasTraumaCenter() { return hasTraumaCenter; }
    public String[] getSpecialties() { return specialties; }

    @Override
    public String toString() {
        return name + " - Beds: " + availableBeds + "/" + totalBeds +
                " - " + location + " - Specialties: " + String.join(", ", specialties);
    }
}