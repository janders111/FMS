package db;

import model.Event;
import model.Person;

/**
 * Class for interfacing or doing anything with the event table in our database.
 */
public class EventDAO {
    /**
     * Add a life event to a corresponding person.
     * @param p Person that you want to add an event too.
     * @param e Event you want to add to that person's life.
     * @return True if event was successfully added.
     */db/EventDAO.java:15
    public boolean addEventToPerson(Person p, Event e) {
        return true;
    }

    /**
     * Get the event object, given the event ID
     * @param eventID ID of the event object you want
     * @return the found event object, or null if not found.
     */
    public Event getEvent(String eventID) {
        return null;
    }
    public Event createEvent(Event e) {
        return null;
    }
    public boolean deleteEvent(Event e) {
        return true;
    }
}
