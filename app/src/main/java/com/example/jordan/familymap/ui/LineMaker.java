package com.example.jordan.familymap.ui;

import android.content.Context;

import com.example.jordan.familymap.model.FilterManager;
import com.example.jordan.familymap.model.LineColors;
import com.example.jordan.familymap.model.MainModel;
import com.example.jordan.familymap.model.SettingsManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import model.Event;
import model.Person;

public class LineMaker {
    private static ArrayList<Polyline> lines = new ArrayList<>();
    static int familyLinesStartWidth = 25;
    static int minLineWidth = 2;

    static public Comparator<Event> eventComparator = new Comparator<Event>() {
        public int compare(Event e1, Event e2) {
            return e1.getYear() - e2.getYear();
        }
    };

    public static void setLines(Event e, Person p, GoogleMap gm) {
        if(lines.size() > 0) {
            for(Polyline pLine : lines) {
                pLine.remove();
            }
            lines.clear();
        }

        if(SettingsManager.getSpouseLines() == true) {
            addSpouseLines(e, p, gm);
        }
        if(SettingsManager.getFamilyTreeLines() == true) {
            addFamilyTree(e, p, gm, familyLinesStartWidth);
        }
        if(SettingsManager.getLifeStoryLines() == true) {
            addStoryLines(e, p, gm);
        }
    }

    private static void addSpouseLines(Event e, Person p, GoogleMap gm) {
        if(p.getSpouse() == null)
            return;
        if(p.getSpouse().equals(""))
            return;
        for(Person potentialSpouse : FilterManager.getFilteredPeople()) {
            if(potentialSpouse.getPersonID().equals(p.getSpouse())) {
                addSpouseLinesHelper(e, potentialSpouse, gm);
            }
        }
    }

    private static void addSpouseLinesHelper(Event e1,  Person p2, GoogleMap gm) {
        Event e2 = getEarliestEvent(p2.getPersonID());
        if(e1 == null || e2 == null)
            return;
        addLineBetween(e1, e2, LineColors.getColorSL(), gm);
    }

    private static void addFamilyTree(Event e1, Person p, GoogleMap gm, int width) {
        if(width < minLineWidth) {
            width = minLineWidth;
        }
        if (e1 == null)
            return;
        if(p.getFather() != null) {
            Event fatherBirth = getEarliestEvent(p.getFather());
            if(fatherBirth != null) {
                addLineWithWidth(e1, fatherBirth, LineColors.getColorFT(), gm, width);
                Person father = MainModel.getPersonIDtoPerson().get(p.getFather());
                addFamilyTree(fatherBirth, father, gm, width - 8);
            }
        }
        if(p.getMother() != null) {
            Event motherBirth = getEarliestEvent(p.getMother());
            if(motherBirth != null) {
                addLineWithWidth(e1, motherBirth, LineColors.getColorFT(), gm, width);
                Person mother = MainModel.getPersonIDtoPerson().get(p.getMother());
                addFamilyTree(motherBirth, mother, gm, width - 8);
            }
        }
    }

    private static void addStoryLines(Event e, Person p, GoogleMap gm) {
        ArrayList<Event> personEvents = new ArrayList<>();
        personEvents = FilterManager.getPersonToFilteredEvents(p.getPersonID());
        if(personEvents == null)
            return;
        if(personEvents.size() < 2)
            return;
        Context mContext;
        personEvents.sort(eventComparator);
        for(int i = 0; i < personEvents.size() - 1; i++) {
            addLineBetween(personEvents.get(i), personEvents.get(i+1), LineColors.getColorLS(), gm);
        }
    }

    private static void addLineBetween(Event e1, Event e2, int color, GoogleMap gm) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(false)
                .add(
                        new LatLng(e1.getLatitude(), e1.getLongitude()),
                        new LatLng(e2.getLatitude(), e2.getLongitude()));
        Polyline poly = gm.addPolyline(polylineOptions);
        poly.setColor(color);
        lines.add(poly);
    }

    private static void addLineWithWidth(Event e1, Event e2, int color, GoogleMap gm, int width) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(false)
                .add(
                        new LatLng(e1.getLatitude(), e1.getLongitude()),
                        new LatLng(e2.getLatitude(), e2.getLongitude()));
        Polyline poly = gm.addPolyline(polylineOptions);
        poly.setColor(color);
        poly.setWidth(width);
        lines.add(poly);
    }

    private static Event getEarliestEvent(String personID) {
        ArrayList<Event> personEvents = new ArrayList<>();
        personEvents = FilterManager.getPersonToFilteredEvents(personID);
        if(personEvents == null)
            return null;
        if(personEvents.isEmpty())
            return null;
        Context mContext;
        personEvents.sort(eventComparator);
        return personEvents.get(0);
    }
}
