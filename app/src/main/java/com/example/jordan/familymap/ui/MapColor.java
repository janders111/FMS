package com.example.jordan.familymap.ui;

import com.example.jordan.familymap.model.CurrentColor;
import com.example.jordan.familymap.model.MainModel;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MapColor {
    private float counter = 0;
    private float incrementer = 44;
    private float hue;

    /**
     * when initialized, set myself to a random color
     * @return
     */

    public void setMeToRandomHue() {
        hue = CurrentColor.currentColor;
        CurrentColor.next();
    }

    public float getHue() {
        return hue;
    }
}
