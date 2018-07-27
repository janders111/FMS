package service;

import junit.framework.TestCase;

import java.io.File;

/**
 * This will only fail if exceptions are thrown.
 * Check that names are printed to the console.
 */
public class GetNameTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testJsonToClass() throws Exception{
        String[] fNamesArray = GetName.jsonToClass("json" + File.separator + "fnames.json").data;
        assertFalse("\nClass returned an empty array of names",fNamesArray.length == 0);
        System.out.println("\nClass returned an array of " + fNamesArray.length + " names.");
    }

    public void testGetFemaleName() {
        System.out.println("\nFemale names");
        System.out.println(GetName.getFemaleName());
        System.out.println(GetName.getFemaleName());
        System.out.println(GetName.getFemaleName());
        System.out.println(GetName.getFemaleName());
    }

    public void testGetMaleName() {
        System.out.println("\nMale names");
        System.out.println(GetName.getMaleName());
        System.out.println(GetName.getMaleName());
        System.out.println(GetName.getMaleName());
        System.out.println(GetName.getMaleName());
    }

    public void testGetSurname() {
        System.out.println("\nSurnames");
        System.out.println(GetName.getSurname());
        System.out.println(GetName.getSurname());
        System.out.println(GetName.getSurname());
        System.out.println(GetName.getSurname());
    }
}