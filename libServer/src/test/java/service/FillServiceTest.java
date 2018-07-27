package service;

import junit.framework.TestCase;
import java.sql.Connection;
import db.DBConnManager;
import db.EventDAO;
import db.UserDAO;
import model.User;
import service.ReqAndResponses.FillRequest;
import service.ReqAndResponses.ResponseMessage;

public class FillServiceTest extends TestCase {
    FillRequest req = new FillRequest("n", 4);
    FillRequest reqInvalidUsername = new FillRequest("asdf", 4);
    FillRequest reqInvalidNumGenerations = new FillRequest("n", -1);

    public void setUp() throws Exception {
        super.setUp();
        User u = new User("n", "p", "e", "f",
                                        "l", "m", "id") ;
        Connection conn = DBConnManager.getConnection();
        DBConnManager.clearTables(conn);
        DBConnManager.closeConnection(conn, true);
        conn = DBConnManager.getConnection();
        UserDAO.createUser(u, conn);
        DBConnManager.closeConnection(conn, true);
    }

    /**
     * Tests that fill returns an error message when it should.
     * Tests both error and non-error cases
     * @throws Exception
     */
    public void testFill() throws Exception {
        boolean threw = false;
        //test return object
        ResponseMessage response = FillService.fill(req);
        ResponseMessage expectedResponse = new ResponseMessage("Successfully added 30 persons " +
                                                "and 91 events to the database.", false);
        assertTrue("Returned response message from testFill incorrect"
                                                , expectedResponse.equals(response));

        response = (ResponseMessage)FillService.fill(reqInvalidUsername);
        assertTrue("failed invalid username test", response.isError());

        response = (ResponseMessage)FillService.fill(reqInvalidNumGenerations);
        assertTrue("failed invalid numGenerations test", response.isError());
    }

    /**
     * Tests both error and non-error cases
     *
     * This function adds events for user u (created above).
     * If the last assert returns 91, that means that all 3 types of events were able to be created.
     * This fucion also tests its helper functions addBirth, addMarriage, and addDeath.
     *
     * The number of added events should be (numAddedPeople*3) + 1. This is because 1 birthday is
     * added for the user, and for all of the other people, three events are added. For the last
     * test, 30 people are added. 30*3 + 1 = 91.
     *
     * If you want to view events in detail, use the test website.
     *
     * @throws Exception
     */
    public void testGenerateEventsValid() throws Exception{
        Connection conn = DBConnManager.getConnection();
        int numGenerationsBefore = EventDAO.getNumEvents("n", conn);
        DBConnManager.closeConnection(conn, true);

        FillService.fill(req);

        conn = DBConnManager.getConnection();
        int numGenerationsAfter = EventDAO.getNumEvents("n", conn);
        DBConnManager.closeConnection(conn, true);

        int numGenerations = numGenerationsAfter - numGenerationsBefore;
        assertTrue("number of new events created not correct.",  numGenerations == 91);
    }

    public void testGenerateEvents() throws Exception{
        FillRequest fillReqInvalidUsername = new FillRequest("asdf", 3);
        ResponseMessage response = (ResponseMessage)FillService.fill(fillReqInvalidUsername);
        assertTrue("Did not return error when given bad username", response.isError());

        FillRequest fillReqInvalidNumGenerations = new FillRequest("asdf", -999);
        response = (ResponseMessage)FillService.fill(fillReqInvalidUsername);
        assertTrue("Did not return error when given bad numGenerations", response.isError());
    }
}