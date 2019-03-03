package Model;

/**
 * This class is the model class that holds the event information
 */
public class Event {
    /**
     * Unique identifier for this event (non-empty string)
     */

    private String descendant;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    private String eventID;
    /**
     * Year in which event occurred
     */
    private Integer year;
    /**
     * ID of person to which this event belongs
     */
    private String person;
    /**
     *  Latitude of event’s location
     */
    private Double latitude;
    /**
     * Longitude of event’s location
     */
    private Double longitude;
    /**
     * Country in which event occurred
     */
    private String country;
    /**
     * City in which event occurred
     */
    private String city;
    /**
     *Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;

    public Event(String eventID, String descendant, String person, Double latitude, Double longitude, String country, String city, String eventType, Integer year) {
        this.descendant = descendant;
        this.eventID = eventID;
        this.year = year;
        this.person = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventID() {
        return eventID;
    }

    public Integer getYear() {
        return year;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event newEvent = (Event) o;
            if (newEvent.getEventID().equals(getEventID()) &&
                    newEvent.getDescendant().equals(getDescendant()) &&
                    newEvent.getPerson().equals(getPerson()) &&
                    newEvent.getLatitude().equals(getLatitude()) &&
                    newEvent.getLongitude().equals(getLongitude()) &&
                    newEvent.getCountry().equals(getCountry()) &&
                    newEvent.getCity().equals(getCity()) &&
                    newEvent.getEventType().equals(getEventType()) &&
                    newEvent.getYear().equals(getYear())) {
                return true;
            }
        }
        return false;
    }
}
