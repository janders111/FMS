package com.example.jordan.familymap.model;

import android.widget.Toast;
import com.example.jordan.familymap.ui.MapColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginResponse;
import ReqAndResponses.PersonAllEventsResponse;
import ReqAndResponses.PersonAllFamilyResponse;
import ReqAndResponses.PersonRequest;
import model.Event;
import model.Person;

/**
 * caches the data for the user's login session
 */
public class MainModel implements com.example.jordan.familymap.async.FillAllDataTask.fillDataInterface{
    private static Event[] events;
    private static Person[] people;
    private static Map<String, Person> personIDtoPerson = new HashMap<String, Person>();;
    private static Map<String, Event> eventIDToEvent = new HashMap<String, Event>();;
    private static Map<String, ArrayList<Event>> personToEvents = new HashMap<String, ArrayList<Event>>();;
    //private static Settings settings
    //private static Filter filter
    private static ArrayList<String> eventTypes = new ArrayList<String>(); //arrayList, every type of event ex: birth, death, etc.
    private static Map<String, MapColor> eventTypeColors = new HashMap<String, MapColor>();
    private static Person user;
    private static Set<String> paternalAncestors = new HashSet<String>();
    private static Set<String> maternalAcnestors = new HashSet<String>();
    private static Map<String, ArrayList<Person>> personToChildren = new HashMap<String, ArrayList<Person>>();
    private static String userName;
    private static String authToken;
    private static String usersID;

    private MainModel() { }

    /**
     * This function initializes most of the variables/data above. It must be run as async.
     * @param lr
     * @return
     */
    public static String fillAllData(LoginResponse lr) {
        String error = "";
        userName = lr.getUserName();
        authToken = lr.getAuthToken();
        usersID = lr.getPersonID();

        ProxyServer.setAuthToken(authToken);
        PersonAllFamilyResponse famRes = ProxyServer.getUsersPeople();
        if(famRes != null)
            people = famRes.getPersonArray();
        else
            error += "Error getting family. ";

        PersonAllEventsResponse eventsRes = ProxyServer.getUsersEvents();
        if(eventsRes != null)
            events = eventsRes.getEventArray();
        else
            error += "Error getting events. ";

        secondaryFillAllData();
        return error;
    }

    public static void filterMales() {
        ArrayList<Event> arrl = new ArrayList<>();
        for (Event p : events) {
            if(p.getCountry().equals("Brazil")) {
                arrl.add(p);
            }
            events = new Event[arrl.size()];
            int i = 0;
            for(Event ev : arrl) {
                events[i] = ev;
                i++;
            }
        }
    }

    public static void filterMalesReal() {
        ArrayList<Person> arrl = new ArrayList<>();
        for (Person p : people) {
            if(p.getGender().equals("m")) {
                arrl.add(p);
            }
            people = new Person[arrl.size()];
            int i = 0;
            for(Person pers : arrl) {
                people[i] = pers;
                i++;
            }
        }
    }

    private static void secondaryFillAllData(){
        for(Person p : people) {
            if(p.getPersonID().equals(usersID)) {
                user = p;
                break;
            }
        }
        for(Person p : people) {
            personIDtoPerson.put(p.getPersonID(), p);
        }
        for(Event e : events) {
            eventIDToEvent.put(e.getEventID(), e);
//            for(String type : eventTypes) {
//                Boolean alreadyAdded = type.toLowerCase().equals(e.getEventType().toLowerCase());
//                if(alreadyAdded == false) {
//                    eventTypes.add(e.getEventType());
//                }
//            }
            if(!eventTypes.contains(e.getEventType())) {
                eventTypes.add(e.getEventType());
            }
        }
        for(Person p : people) {
            String personID = p.getPersonID();
            ArrayList<Event> personEvents = new ArrayList<Event>();
            for(Event e: events) {
                if(personID.equals(e.getPersonID())) {
                    personEvents.add(e);
                }
            }
            personToEvents.put(personID, personEvents);
        }

        //paternal Ancestors
        Person currentPerson = user;
        while(true){
            String fatherID = currentPerson.getFather();
            Person foundAncestor = null;
            for(Person p : people) {
                if(fatherID != null) {
                    if(fatherID.equals(p.getPersonID())) {
                        foundAncestor = p;
                        break;
                    }
                }
            }
            if(foundAncestor != null) {
                paternalAncestors.add(foundAncestor.getPersonID());
                currentPerson = foundAncestor;
            }
            else {
                break;
            }
        }

        currentPerson = user;
        while(true){
            String motherID = currentPerson.getMother();
            Person foundAncestor = null;
            for(Person p : people) {
                if(motherID != null) {
                    if(motherID.equals(p.getPersonID())) {
                        foundAncestor = p;
                        break;
                    }
                }
            }
            if(foundAncestor != null) {
                maternalAcnestors.add(foundAncestor.getPersonID());
                currentPerson = foundAncestor;
            }
            else {
                break;
            }
        }

        //get children for every user
        for(Person p : people) {
            String parentID = p.getPersonID();
            ArrayList<Person> children = new ArrayList<Person>();
            for(Person potentialChild : people) {
                if(potentialChild.getFather() != null) {
                    if (potentialChild.getFather().equals(parentID) ||
                            potentialChild.getMother().equals(parentID)) {
                        children.add(potentialChild);
                    }
                }
            }
            personToChildren.put(parentID, children);
        }

        //set colors
        for(String eventType : eventTypes) {
            MapColor color = new MapColor();
            color.setMeToRandomHue();
            eventTypeColors.put(eventType, color);
        }

        FilterManager.init();
    }

    @Override
    public void fillDataInterfaceResult(String error) {//runs once fillAllData is finished.
        if(!error.equals("")) {
            System.out.println(error);
        }
        System.out.println(people.length + " people saved to phone");
    }

    public static Event[] getEvents() {
        return events;
    }

    public static Person[] getPeople() {
        return people;
    }

    public static Map<String, Person> getPersonIDtoPerson() {
        return personIDtoPerson;
    }

    public static Map<String, Event> getEventIDToEvent() {
        return eventIDToEvent;
    }

    public static Map<String, ArrayList<Event>> getPersonToEvents() {
        return personToEvents;
    }

    public static ArrayList<String> getEventTypes() {
        return eventTypes;
    }

    public static Person getUser() {
        return user;
    }

    public static Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public static Set<String> getMaternalAcnestors() {
        return maternalAcnestors;
    }

    public static Map<String, ArrayList<Person>> getPersonToChildren() {
        return personToChildren;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static String getUsersID() {
        return usersID;
    }

    public static Map<String, MapColor> getEventTypeColors() {
        return eventTypeColors;
    }
}
