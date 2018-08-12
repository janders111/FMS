package com.example.jordan.familymap.model;

public class SettingsManager {
    private static Boolean lifeStoryLines = true;
    private static Boolean familyTreeLines = true;
    private static Boolean spouseLines = true;

    private static int currentMapType = 1; //an int from one to four

    public static Boolean getLifeStoryLines() {
        return lifeStoryLines;
    }

    public static void setLifeStoryLines(Boolean lifeStoryLines) {
        SettingsManager.lifeStoryLines = lifeStoryLines;
    }

    public static Boolean getFamilyTreeLines() {
        return familyTreeLines;
    }

    public static void setFamilyTreeLines(Boolean familyTreeLines) {
        SettingsManager.familyTreeLines = familyTreeLines;
    }

    public static Boolean getSpouseLines() {
        return spouseLines;
    }

    public static void setSpouseLines(Boolean spouseLines) {
        SettingsManager.spouseLines = spouseLines;
    }

    public static int getCurrentMapType() {
        return currentMapType;
    }

    public static void setCurrentMapType(int currentMapType) {
        SettingsManager.currentMapType = currentMapType;
    }
}
