package model;
/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called Events.
 */
public class Event {
    String EventID;
    String Descendant	;
    String Person	;
    float Latitude;
    float Longitude;
    String Country;
    String City;
    String EventType;
    int Year;

    /**
     * @param eventID Unique ID for event
     * @param descendant Name of user account this event belongs to (nonempty)
     * @param person person this event belongs to (non-empty string)
     * @param latitude Latitude of the event’s location
     * @param longitude Longitude of the event’s location
     * @param country Name of country where event occurred  (non-empty string)
     * @param city Name of city where event occurred (non-empty string)
     * @param eventType Ex: baptism, mairrage.
     * @param year Year the event occurred (integer formatted as string)
     */

    public Event(String eventID, String descendant, String person, float latitude, float longitude, String country, String city, String eventType, int year) {
        EventID = eventID;
        Descendant = descendant;
        Person = person;
        Latitude = latitude;
        Longitude = longitude;
        Country = country;
        City = city;
        EventType = eventType;
        Year = year;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getDescendant() {
        return Descendant;
    }

    public void setDescendant(String descendant) {
        Descendant = descendant;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
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
}
