package com.example.jordan.familymap.model;

import android.widget.Toast;

import com.example.jordan.familymap.ui.LoginFrag;
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
public class MainModel implements com.example.jordan.familymap.async.FillAllDataTask.fillDataInterface {
    private static LoginFrag lf;
    private static Event[] events;
    private static Person[] people;
    private static Map<String, Person> personIDtoPerson = new HashMap<String, Person>();
    private static Map<String, Event> eventIDToEvent = new HashMap<String, Event>();
    private static ArrayList<String> eventTypes = new ArrayList<String>(); //arrayList, every type of event ex: birth, death, etc.
    private static Map<String, MapColor> eventTypeColors = new HashMap<String, MapColor>();
    private static Person user;
    private static Set<String> paternalAncestors = new HashSet<String>();
    private static Set<String> maternalAcnestors = new HashSet<String>();
    private static Map<String, ArrayList<Person>> personToChildren = new HashMap<String, ArrayList<Person>>();
    private static String userName;
    private static String password;
    private static String authToken;
    private static String usersID;

    private MainModel() {
    }

    public static void clear() {
        Event[] events = null;
        Person[] people = null;
        personIDtoPerson =  new HashMap<String, Person>();
        eventIDToEvent = new HashMap<String, Event>();
        eventTypes = new ArrayList<String>(); //arrayList, every type of event ex: birth, death, etc.
        eventTypeColors = new HashMap<String, MapColor>();
        user = null;
        paternalAncestors = new HashSet<String>();
        maternalAcnestors = new HashSet<String>();
        personToChildren = new HashMap<String, ArrayList<Person>>();
        userName = null;
        authToken = null;
        usersID = null;
    }
    /**
     * This function initializes most of the variables/data above. It must be run as async.
     *
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
        if (famRes != null)
            people = famRes.getPersonArray();
        else
            error += "Error getting family. ";

        PersonAllEventsResponse eventsRes = ProxyServer.getUsersEvents();
        if (eventsRes != null)
            events = eventsRes.getEventArray();
        else
            error += "Error getting events. ";

        secondaryFillAllData();
        return error;
    }

    private static void secondaryFillAllData() {
        for (Person p : people) {
            if (p.getPersonID().equals(usersID)) {
                user = p;
                break;
            }
        }
        for (Person p : people) {
            personIDtoPerson.put(p.getPersonID(), p);
        }
        for (Event e : events) {
            eventIDToEvent.put(e.getEventID(), e);

            Boolean alreadyAdded = false;
            for(String type : eventTypes) {
                if(type.toLowerCase().equals(e.getEventType().toLowerCase())) {
                    alreadyAdded = true;
                }
            }
            if(alreadyAdded == false) {
                eventTypes.add(e.getEventType());
            }
        }

        //paternal Ancestors
        Person currentPerson = user;
        while (true) {
            String fatherID = currentPerson.getFather();
            Person foundAncestor = null;
            for (Person p : people) {
                if (fatherID != null) {
                    if (fatherID.equals(p.getPersonID())) {
                        foundAncestor = p;
                        break;
                    }
                }
            }
            if (foundAncestor != null) {
                paternalAncestors.add(foundAncestor.getPersonID());
                currentPerson = foundAncestor;
            } else {
                break;
            }
        }

        currentPerson = user;
        while (true) {
            String motherID = currentPerson.getMother();
            Person foundAncestor = null;
            for (Person p : people) {
                if (motherID != null) {
                    if (motherID.equals(p.getPersonID())) {
                        foundAncestor = p;
                        break;
                    }
                }
            }
            if (foundAncestor != null) {
                maternalAcnestors.add(foundAncestor.getPersonID());
                currentPerson = foundAncestor;
            } else {
                break;
            }
        }

        //get children for every user
        for (Person p : people) {
            String parentID = p.getPersonID();
            ArrayList<Person> children = new ArrayList<Person>();
            for (Person potentialChild : people) {
                if (potentialChild.getFather() != null) {
                    if (potentialChild.getFather().equals(parentID) ||
                            potentialChild.getMother().equals(parentID)) {
                        children.add(potentialChild);
                    }
                }
            }
            personToChildren.put(parentID, children);
        }

        //set colors
        for (String eventType : eventTypes) {
            MapColor color = new MapColor();
            color.setMeToRandomHue();
            eventTypeColors.put(eventType.toLowerCase(), color);
        }
    }

    @Override
    public void fillDataInterfaceResult(String error) {//runs once fillAllData is finished.
        if (!error.equals("")) {
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

    public static String getPassword() { return password; }

    public static void setPassword(String password) { MainModel.password = password; }


    public static float getEventTypeColors(Event e) {
        return eventTypeColors.get(e.getEventType().toLowerCase()).getHue();
    }

    public static ArrayList<Event> getEventsArrayList() {
        ArrayList<Event> tmpArr = new ArrayList<>();
        for (Event e : MainModel.getEvents())
            tmpArr.add(e);
        return tmpArr;
    }

    public static ArrayList<Person> getPeopleArrayList() {
        ArrayList<Person> tmpArr = new ArrayList<>();
        for (Person p : MainModel.getPeople())
            tmpArr.add(p);
        return tmpArr;
    }

    public static LoginFrag getLf() {
        return lf;
    }

    public static void setLf(LoginFrag lfTmp) {
        lf = lfTmp;
    }
}
