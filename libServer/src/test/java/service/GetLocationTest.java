package service;

import junit.framework.TestCase;

/**
 * This will only fail if exceptions are thrown.
 * Check that locations are printed to the console.
 */

public class GetLocationTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Gets and prints a random location with its info.
     * This also tests that helper function jsonToClass works.
     */
    public void testGetLocation() {
        for(int i = 0; i < 2; i++) {
            System.out.println("\nTest getLocation");
            GetLocation.Location l = GetLocation.getLocation();
            assertFalse(l == null);
            System.out.println(l.city);
            System.out.println(l.country);
            System.out.println(l.longitude);
            System.out.println(l.latitude);
        }
    }
}