package com.example.jordan.familymap.model;

import android.content.Context;
import android.graphics.Color;

public class LineColors {
    private static int colorSL = 0;
    private static int colorFT = 2;
    private static int colorLS = 4;

    public static int getColorSL() {
        return convertIndexToColor(colorSL);
    }

    public static int getColorSLIndex() {
        return colorSL;
    }

    public static void setColorSL(int colorAsIndex) {
        colorSL = colorAsIndex;
    }

    public static int getColorFT() {
        return convertIndexToColor(colorFT);
    }

    public static int getColorFTIndex() {
        return colorFT;
    }

    public static void setColorFT(int colorAsIndex) {
        colorFT = colorAsIndex;
    }

    public static int getColorLS() {
        return convertIndexToColor(colorLS);
    }

    public static int getColorLSIndex() {
        return colorLS;
    }

    public static void setColorLS(int colorAsIndex) {
        colorLS = colorAsIndex;
    }

    private static int convertIndexToColor(int colorIndex) {
        switch (colorIndex) {
            case 0:
                return Color.parseColor("red");
            case 1:
                return Color.parseColor("maroon");
            case 2:
                return Color.parseColor("yellow");
            case 3:
                return Color.parseColor("green");
            case 4:
                return Color.parseColor("blue");

                default:return Color.parseColor("red");
        }
    }
}
