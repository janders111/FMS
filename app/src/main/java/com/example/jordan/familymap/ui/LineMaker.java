package com.example.jordan.familymap.ui;

import android.content.Context;

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
    private static ArrayList<Polyline> lines;
    static int familyLinesStartWidth = 25;
    static int minLineWidth = 2;

    static public Comparator<Event> eventComparator = new Comparator<Event>() {
        public int compare(Event e1, Event e2) {
            return e1.getYear() - e2.getYear();
        }
    };

    public static void setLines(Person p, GoogleMap gm) {
        lines = new ArrayList<Polyline>();

        if(SettingsManager.getSpouseLines() == true) {
            addSpouseLines(p, gm);
        }
        if(SettingsManager.getFamilyTreeLines() == true) {
            addFamilyTree(p, gm, familyLinesStartWidth);
        }
        if(SettingsManager.getLifeStoryLines() == true) {
            addStoryLines(p, gm);
        }
    }

    private static void addSpouseLines(Person p, GoogleMap gm) {
        if(p.getSpouse() == null)
            return;
        if(p.getSpouse().equals(""))
            return;
        for(Person potentialSpouse : MainModel.getPeople()) {
            if(potentialSpouse.getPersonID().equals(p.getSpouse())) {
                addSpouseLinesHelper(p, potentialSpouse, gm);
            }
        }
    }

    private static void addSpouseLinesHelper(Person p1, Person p2, GoogleMap gm) {
        Event e1 = getEarliestEvent(p1.getPersonID());
        Event e2 = getEarliestEvent(p2.getPersonID());
        if(e1 == null || e2 == null)
            return;
        addLineBetween(e1, e2, LineColors.getColorSL(), gm);
    }

    private static void addFamilyTree(Person p, GoogleMap gm, int width) {
        if(width < minLineWidth) {
            width = minLineWidth;
        }
        Event e1 = getEarliestEvent(p.getPersonID());
        if (e1 == null)
            return;
        if(p.getFather() != null) {
            Event fatherBirth = getEarliestEvent(p.getFather());
            if(fatherBirth != null) {
                addLineWithWidth(e1, fatherBirth, LineColors.getColorFT(), gm, width);
                Person father = MainModel.getPersonIDtoPerson().get(p.getFather());
                addFamilyTree(father, gm, width - 8);
            }
        }
        if(p.getMother() != null) {
            Event motherBirth = getEarliestEvent(p.getMother());
            if(motherBirth != null) {
                addLineWithWidth(e1, motherBirth, LineColors.getColorFT(), gm, width);
                Person mother = MainModel.getPersonIDtoPerson().get(p.getMother());
                addFamilyTree(mother, gm, width - 8);
            }
        }
    }

    private static void addStoryLines(Person p, GoogleMap gm) {
        ArrayList<Event> personEvents = new ArrayList<>();
        personEvents = MainModel.getPersonToEvents().get(p.getPersonID());
        if(personEvents == null)
            return;
        if(personEvents.size() <= 1)
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
        personEvents = MainModel.getPersonToEvents().get(personID);
        if(personEvents == null)
            return null;
        if(personEvents.isEmpty())
            return null;
        Context mContext;
        personEvents.sort(eventComparator);
        return personEvents.get(0);
    }
}
