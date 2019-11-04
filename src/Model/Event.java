package Model;

/**
 * keeps track of life events for user
 */
public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;
    public Event(String eventID, String associatedUsername, String personID, Double latitude, Double longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
    public String getEventID() {
        return eventID;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public String getUserName() {
        return associatedUsername;
    }
    public void setUserName(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
    public String getPersonID() {
        return personID;
    }
    public void setPID(String personID) {
        this.personID = personID;
    }
    public double getLat() {
        return latitude;
    }
    public void setLat(Double latitude) {}
    public double getLong() {
        return longitude;
    }
    public void setLong(Double longitude) {}
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {}
    public String getCity() {
        return city;
    }
    public void setCity(String city) {}
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String type) {}
    public int getYear() {
        return year;
    }
    public void setYear(int year) {}

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getUserName().equals(getUserName()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    (oEvent.getLat() == getLat()) &&
                    (oEvent.getLong() == getLong()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    (oEvent.getYear() == getYear());
        }
        else {
            return false;
        }
    }
}
