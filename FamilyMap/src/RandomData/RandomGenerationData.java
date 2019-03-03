package RandomData;

public class RandomGenerationData {
    private String[] femaleNames;
    private String[] maleNames;
    private String[] lastNames;
    private Locations[] locations;

    public String[] getFemaleNames() {
        return femaleNames;
    }

    public void setFemaleNames(String[] femaleNames) {
        this.femaleNames = femaleNames;
    }

    public String[] getMaleNames() {
        return maleNames;
    }

    public void setMaleNames(String[] maleNames) {
        this.maleNames = maleNames;
    }

    public String[] getLastNames() {
        return lastNames;
    }

    public void setLastNames(String[] lastNames) {
        this.lastNames = lastNames;
    }

    public Locations[] getLocations() {
        return locations;
    }

    public void setLocations(Locations[] locations) {
        this.locations = locations;
    }

    public RandomGenerationData(String[] femaleNames, String[] maleNames, String[] lastNames, Locations[] locations) {
        this.femaleNames = femaleNames;
        this.maleNames = maleNames;
        this.lastNames = lastNames;
        this.locations = locations;
    }
}
