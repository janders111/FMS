package com.example.jordan.familymap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.Event;
import model.Person;

/**
 * Manages the filtering of events, stores the filters, and can return an array of just the filtered events.
 */
public class FilterManager {
    static public class eventEnabledDisabled{
        public String eventName;
        public Boolean show;
        public eventEnabledDisabled(String eventName, Boolean show) {
            this.eventName = eventName;
            this.show = show;
        }
        public void setShow(Boolean show) {
            this.show = show;
        }
    }
    private static Boolean showMales = true;
    private static Boolean showFemales = true;
    private static Boolean showPaternal = true;
    private static Boolean showMaternal = true;

    private static eventEnabledDisabled[] eventFilters = null;

    public static void clear() {
        showMales = true;
        showFemales = true;
        showPaternal = true;
        showMaternal = true;
        eventFilters = null;
    }

    public static void init() {
        if(eventFilters == null) {
            int numTypes = MainModel.getEventTypes().size();
            eventFilters = new eventEnabledDisabled[numTypes];
            for (int i = 0; i < numTypes; i++) {
                String type = MainModel.getEventTypes().get(i);
                eventFilters[i] = new eventEnabledDisabled(type, true);
            }
        }
    }

    public static void toggleEvent(int index, Boolean show) {
        eventFilters[index].setShow(show);
    }

    public static ArrayList<Event> getFilteredEvents() {
        if(eventFilters == null) {//if init has not been run yet, return all events.
            return MainModel.getEventsArrayList();
        }
        if(allFiltersOff()) {
            return MainModel.getEventsArrayList();
        }
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for(Event e : MainModel.getEvents()) {
            if (eventIsEnabledCustomFilters(e.getEventType()) == false)
                continue;
            Person p = MainModel.getPersonIDtoPerson().get(e.getPersonID());
            if (personIsEnabled(p))
                filteredEvents.add(e);
        }
        return filteredEvents;
    }

    public static ArrayList<Person> getFilteredPeople() {
        if(     FilterManager.getShowMales() &&
                FilterManager.getShowFemales() &&
                FilterManager.getShowPaternal() &&
                FilterManager.getShowMaternal()
                ) {
            return MainModel.getPeopleArrayList();
        }

        ArrayList<Person> filteredPeople = new ArrayList<>();
        for(Person p : MainModel.getPeople()) {
            if (personIsEnabled(p))
                filteredPeople.add(p);
        }
        return filteredPeople;
    }

    private static Boolean personIsEnabled(Person p) {
        if(p.getGender().equals("m") && !FilterManager.getShowMales())
            return false;
        if(p.getGender().equals("f") && !FilterManager.getShowFemales())
            return false;
        if(isMaternal(p) && !FilterManager.getShowMaternal())
            return false;
        if(isPaternal(p) && !FilterManager.getShowPaternal())
            return false;

        return true;
    }

    private static Boolean eventIsEnabledCustomFilters(String eventType) {
        for(eventEnabledDisabled status : eventFilters) {
            if(eventType.toLowerCase().equals(status.eventName.toLowerCase())) {
                return status.show;
            }
        }
        return null;
    }

    public static Boolean eventIsEnabled(int eventType) {
        return eventFilters[eventType].show;
    }

    private static Boolean allFiltersOff() {
        for(eventEnabledDisabled status : eventFilters) {
            if(status.show == false)
                return false;
        }
        return showMales && showFemales && showMaternal && showPaternal;
    }

    public static ArrayList<Event> getPersonToFilteredEvents(String personID) {
        ArrayList<Event> filteredEvents = getFilteredEvents();
        ArrayList<Event> eventsSpecificToThisPerson = new ArrayList<>();
        for (Event e : filteredEvents) {
            if (personID.equals(e.getPersonID())) {
                eventsSpecificToThisPerson.add(e);
            }
        }
        return eventsSpecificToThisPerson;
    }

    private static Boolean isMaternal(Person p) {
        if(MainModel.getMaternalAcnestors().contains(p.getPersonID())){
            return true;
        }
        return false;
    }

    private static Boolean isPaternal(Person p) {
        if(MainModel.getPaternalAncestors().contains(p.getPersonID())){
            return true;
        }
        return false;
    }

//    private static Boolean containsPerson(ArrayList<Person> people, Person p) {
//        for(Person person : people) {
//            if(person.getPersonID().equals(p.getPersonID()))
//                return true;
//        }
//        return false;
//    }


    public static Boolean getShowMales() {
        return showMales;
    }

    public static void setShowMales(Boolean showMales) {
        FilterManager.showMales = showMales;
    }

    public static Boolean getShowFemales() {
        return showFemales;
    }

    public static void setShowFemales(Boolean showFemales) {
        FilterManager.showFemales = showFemales;
    }

    public static Boolean getShowPaternal() {
        return showPaternal;
    }

    public static void setShowPaternal(Boolean showPaternal) {
        FilterManager.showPaternal = showPaternal;
    }

    public static Boolean getShowMaternal() {
        return showMaternal;
    }

    public static void setShowMaternal(Boolean showMaternal) {
        FilterManager.showMaternal = showMaternal;
    }
}
