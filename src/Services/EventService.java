package Services;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.EventDAO;
import Model.AuthTok;
import Model.Event;
import Results.EventResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Grabs all events of one user by ID
 */
public class EventService {

private  String checkToken(String authTok) throws Exception {
        Database db = new Database();
        AuthTok token;
        try {
            Connection connect = db.openConnection();
            AuthTokDAO aDAO = new AuthTokDAO(connect);
            token = aDAO.findToken(authTok);
            db.closeConnection(true);
        }
        catch(DatabaseAccessException e) {
            db.closeConnection(false);
            throw new DatabaseAccessException("Error: " + e.getMessage());
        }
        if(token != null) {
            return token.getUsername();
        }
        else {
            return null;
        }
    }

    public EventResult getEventsForUser(String authTok) throws Exception {
        EventResult result;
        Database db = new Database();
        String username = checkToken(authTok);
        ArrayList<Event> eventList;

        try {
            if (username == null) {
                throw new DatabaseAccessException("Invalid authorization token.");
            }
            Connection connect = db.openConnection();
            EventDAO eDAO = new EventDAO(connect);
            eventList = eDAO.findAll(username);
            db.closeConnection(true);
            if(eventList == null || eventList.size() == 0) {
                throw new DatabaseAccessException("No events exist for this user.");
            }
            else {
                Event[] events = eventList.toArray(new Event[eventList.size()]);
                result = new EventResult(events);
            }
        }
        catch (DatabaseAccessException e) {
            result = new EventResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
