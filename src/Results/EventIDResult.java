package Results;

public class EventIDResult extends Result {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private String authToken;
    private String message;

    public EventIDResult(String eventID, String associatedUsername, String personID, Double latitude, Double longitude,
                         String country, String city, String eventType, int year) {
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
    public EventIDResult(String message) {
        this.message = message;
    }
    public void setID(String eventID) {
        this.eventID = eventID;
    }
    public void setAuthTok(String authToken) {
        this.authToken = authToken;
    }
    public String getEventID() { return eventID; }
    public String getAuthToken() { return authToken; }
    public String getUsername() { return associatedUsername; }
    public String getPersonID() { return personID; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getEventType() { return eventType; }
    public int getYear() { return year; }
}
