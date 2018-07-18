package service.ReqAndResponses;

/**
 * Response information from event function.
 */
public class EventResponse {
    String Descendant;
    String EventID;
    String PersonID;
    Float Latitude;
    Float Longitude;
    String Country;
    String City;
    String EventType;
    int Year;
    String Message;

    /**
     *
     * @param descendant Name of user account this event belongs to (nonempty)
     * @param eventID  Event’s unique ID (non-empty string)
     * @param personID ID of the person this event belongs to (non-empty string)
     * @param latitude Latitude of the event’s location
     * @param longitude Longitude of the event’s location
     * @param country Name of country where event occurred  (non-empty string)
     * @param city Name of city where event occurred (non-empty string)
     * @param eventType  Type of event ("birth", "baptism", etc.) (non-empty string)
     * @param year Year the event occurred (integer formatted as string)
     * @param message Message describing error. If there was no error, this will be null.
     */
    public EventResponse(String descendant, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, int year, String message) {
        Descendant = descendant;
        EventID = eventID;
        PersonID = personID;
        Latitude = latitude;
        Longitude = longitude;
        Country = country;
        City = city;
        EventType = eventType;
        Year = year;
        Message = message;
    }

    public String getDescendant() {
        return Descendant;
    }

    public void setDescendant(String descendant) {
        Descendant = descendant;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public void setLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public void setLongitude(Float longitude) {
        Longitude = longitude;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
