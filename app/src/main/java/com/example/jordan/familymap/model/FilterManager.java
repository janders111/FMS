package com.example.jordan.familymap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.Event;

/**
 * Manages the filtering of events, stores the filters, and can return an array of just the filtered events.
 */

public class FilterManager {
    private static Map<String, ArrayList<Event>> eventTypeToEvents;
    private static Map<String, Boolean> eventFilters;

    public static void init() {
        eventTypeToEvents = new HashMap<String, ArrayList<Event>>();
        eventFilters = new HashMap<String, Boolean>();
        for(String eventType : MainModel.getEventTypes()) {
            eventFilters.put(eventType.toLowerCase(), false);
        }
        for(String eventType : MainModel.getEventTypes()) {
            eventTypeToEvents.put(eventType.toLowerCase(), new ArrayList<Event>());
        }
        for(Event e : MainModel.getEvents()) {
            String myEventType = e.getEventType().toLowerCase();
            eventTypeToEvents.get(myEventType).add(e);
        }
    }

    public static ArrayList<Event> getFilteredEvents() {
        ArrayList<Event> filteredEvents = new ArrayList<Event>();
        Iterator it = eventFilters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((Boolean) pair.getValue()) {
                String eventType = (String)pair.getKey();
                ArrayList<Event> eventsToAdd = eventTypeToEvents.get(eventType);
                for(Event e : eventsToAdd) {
                    filteredEvents.add(e);
                }
            }
        }
        return filteredEvents;
    }

    public static Map<String, ArrayList<Event>> getEventTypeToEvents() {
        return eventTypeToEvents;
    }

    public static Map<String, Boolean> getEventFilters() {
        return eventFilters;
    }
}
