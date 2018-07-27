package model;

import java.util.Objects;

/**
 * This class holds data that is being passed too and from the dao. Modeled after the table called Events.
 */
public class Event {
    String eventID;
    String descendant	;
    String personID	;
    float latitude;
    float longitude;
    String country;
    String city;
    String eventType;
    int year;

    /**
     * @param eventID Unique ID for event
     * @param descendant Name of user account this event belongs to (nonempty)
     * @param personID person this event belongs to (non-empty string)
     * @param latitude Latitude of the event’s location
     * @param longitude Longitude of the event’s location
     * @param country Name of country where event occurred  (non-empty string)
     * @param city Name of city where event occurred (non-empty string)
     * @param eventType Ex: baptism, mairrage.
     * @param year Year the event occurred (integer formatted as string)
     */
    public Event(String eventID, String descendant, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
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

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        Boolean ret =  Float.compare(event.latitude, latitude) == 0 &&
                Float.compare(event.longitude, longitude) == 0 &&
                year == event.year &&
                Objects.equals(eventID, event.eventID) &&
                Objects.equals(descendant, event.descendant) &&
                Objects.equals(personID, event.personID) &&
                Objects.equals(country, event.country) &&
                Objects.equals(city, event.city) &&
                Objects.equals(eventType, event.eventType);
        return ret;
    }

    @Override
    public int hashCode() {

        return Objects.hash(eventID, descendant, personID, latitude, longitude, country, city, eventType, year);
    }
}
