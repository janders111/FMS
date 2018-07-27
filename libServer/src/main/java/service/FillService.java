package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import db.DBConnManager;
import db.EventDAO;
import db.PersonDAO;
import db.UserDAO;
import handlers.ObjEncoderDecoder;
import model.Event;
import model.Person;
import model.User;
import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.ResponseMessage;

public class FillService {
    /**
     * Populates the server's database with generated data for the specified user name.
     The required "username" parameter must be a user already registered with the server. If there
     is any data in the database already associated with the given user name, it is deleted. The
     optional "generations" parameter lets the caller specify the number of generations of ancestors
     to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
     persons each with associated events).
     * @param r See documentation of this object in ReqAndResponses package for more info.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    
    public static ResponseMessage fill(FillRequest r) {
        Connection conn = null;
        int numPeople = 0;
        int numEventsAdded = 0;
        int numEventsBefore;
        User u;

        try {
            if(r.getNumGenerations() < 1) {
                throw new IOException("numGenerations must be greater than zero");
            }
            if(r.getNumGenerations() > 25) {
                throw new IOException("numGenerations too high");
            }
            conn = DBConnManager.getConnection();
            u = UserDAO.getUser(r.getUserName());
            try {
                //delete existing data for the user
                PersonDAO.deleteUsersPeople(r.getUserName(), conn);
                EventDAO.deleteUsersEvents(r.getUserName(), conn);
                DBConnManager.closeConnection(conn, true);
                conn = DBConnManager.getConnection();
                //keep track of number of events for the response message.
                numEventsBefore = EventDAO.getNumEvents(r.getUserName(), conn);
                String momName = GetName.getFemaleName();
                String dadName = GetName.getMaleName();
                //add the user to the tree, and their birth event.
                Person p = ObjEncoderDecoder.createPersonFromUser(u, momName, dadName, conn);
                addBirth(p, 1996, conn); //the user needs a bday
                numPeople = generateTree(p, r.getNumGenerations(), 1996, conn);
                numEventsAdded = EventDAO.getNumEvents(r.getUserName(), conn) - numEventsBefore;
            } catch (IOException e) {
                throw new IOException("Error creating data.");
            }
            return new ResponseMessage("Successfully added " + numPeople + " persons and "
                    + numEventsAdded + " events to the database.", false);
        }
        catch(Exception e) {
            return new ResponseMessage(e.toString(), true);
        }
        finally {
            try{DBConnManager.closeConnection(conn, true);}
            catch(Exception e){e.printStackTrace();}
        }
    }

    /**
     * Recursive function that creates a family tree and associated events.
     * @param p Root person
     * @param numGenerations How many levels in the family tree
     * @param startDate DOB of the root. This date gets subtracted from as the people in the
     *                 tree get older.
     * @param conn
     * @return Number of people added too the tree.
     * @throws Exception
     */
    public static int generateTree(Person p, int numGenerations, int startDate, Connection conn) throws Exception {
        int numPeopleAdded = 0;
        //base case
        if(numGenerations < 1) {
            return 0;
        }
        //generate Mother
        int ageWhenHadChildren = ThreadLocalRandom.current().nextInt(20, 40);
        int momDOB = startDate - ageWhenHadChildren;
        UUID momUUID = UUID.randomUUID();
        UUID dadUUID = UUID.randomUUID();
        Person mother = new Person(p.getMother(), p.getDescendant(), GetName.getFemaleName(),
                p.getLastName(), "f", momUUID.toString(), dadUUID.toString(), p.getFather());
        PersonDAO.createPerson(mother, conn);
        GetLocation.Location marriageLocation = GetLocation.getLocation();
        int DOB = startDate - ageWhenHadChildren;
        int marriageDate = DOB + ThreadLocalRandom.current().nextInt(20, 40);
        try {
            addMarriage(mother, marriageLocation, marriageDate, conn);
            generateBirthDeath(mother, DOB, conn);
        }
        catch (Exception e){
            throw new Exception("Failed to generate new events.");
        }
        //generate Father
        ageWhenHadChildren = ThreadLocalRandom.current().nextInt(20, 40);
        int dadDOB = startDate - ageWhenHadChildren;
        momUUID = UUID.randomUUID();
        dadUUID = UUID.randomUUID();
        Person father = new Person(p.getFather(), p.getDescendant(), GetName.getMaleName(),
                p.getLastName(), "m", momUUID.toString(), dadUUID.toString(), p.getMother());
        PersonDAO.createPerson(father, conn);
        try {
            generateBirthDeath(father, startDate - ageWhenHadChildren, conn);
            addMarriage(father, marriageLocation, marriageDate, conn);
        }
        catch (Exception e){
            throw new Exception("Failed to generate new events.");
        }
        //RECURSE
        numPeopleAdded += generateTree(mother,numGenerations - 1, momDOB, conn);
        numPeopleAdded += generateTree(father,numGenerations - 1, dadDOB, conn);
        return numPeopleAdded + 2;
    }

    public static void generateBirthDeath(Person p, int DOB, Connection conn) throws SQLException {
        addBirth(p, DOB, conn);
        addDeath(p, DOB, conn);
    }

    public static void addBirth(Person p, int DOB, Connection conn) throws SQLException {
        UUID eventID = UUID.randomUUID();
        GetLocation.Location location = GetLocation.getLocation();
        Event e = new Event(eventID.toString(), p.getDescendant(), p.getPersonID(), location.latitude,
                location.longitude, location.country, location.city, "Birth", DOB);
        EventDAO.createEvent(e, conn);
    }

    public static void addMarriage(Person p, GetLocation.Location location, int marriageDate, Connection conn) throws SQLException {
        UUID eventID = UUID.randomUUID();
        Event e = new Event(eventID.toString(), p.getDescendant(), p.getPersonID(), location.latitude,
                location.longitude, location.country, location.city, "Marriage", marriageDate);
        EventDAO.createEvent(e, conn);
    }

    public static void addDeath(Person p, int DOB, Connection conn) throws SQLException {
        UUID eventID = UUID.randomUUID();
        GetLocation.Location location = GetLocation.getLocation();
        int deathDate = DOB + ThreadLocalRandom.current().nextInt(40, 110);
        Event e = new Event(eventID.toString(), p.getDescendant(), p.getPersonID(), location.latitude,
                location.longitude, location.country, location.city, "Death", deathDate);
        EventDAO.createEvent(e, conn);
    }
}
