package service;
import java.sql.Connection;

import db.*;

import service.ReqAndResponses.ResponseMessage;
/** Deletes ALL data from the database, including user accounts, auth tokens, and
 generated person and event data.*/
public class ClearService {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and
     generated person and event data, then recreates the tables.
     * @return See documentation of this object in ReqAndResponses package for more info.
     */
    public static ResponseMessage clear() {
        Connection conn = null;
        System.out.println("clear in ClearService called.");
        try {
            conn = DBConnManager.getConnection();
            DBConnManager.clearTables(conn);
            return new ResponseMessage("Clear succeeded.", false);
        }
        catch(Exception e){
            //System.out.println(e.toString());
            //e.printStackTrace();
            return new ResponseMessage("Clear failed. Server error.", true);
        }
        finally {
            try{DBConnManager.closeConnection(conn, true);}
            catch(Exception e){e.printStackTrace();}
        }
    }
}
