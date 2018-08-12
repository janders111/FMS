package com.example.jordan.familymap.model;

/**
 * static class that holds and generates the colors of the markers
 * Part of its function is to prevent colors from being too close together.
 */
public class CurrentColor {
    public static float currentColor = 0;

    public static void next() {
        currentColor = currentColor + 25;
        currentColor = currentColor % 360;
    }
}
