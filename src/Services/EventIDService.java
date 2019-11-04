package Services;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.EventDAO;
import Model.AuthTok;
import Model.Event;
import Results.EventIDResult;

import java.sql.Connection;

//Returns ONE event
public class EventIDService { //REQUIRES AUTHTOK

    public EventIDResult getEvent(String authTok, String eventID) throws Exception {
        Database db = new Database();
        EventIDResult result;
        Event event;

        try {
            Connection connect = db.openConnection();
            EventDAO eDAO = new EventDAO(connect);
            AuthTokDAO aDAO = new AuthTokDAO(connect);
            event = eDAO.readEvent(eventID);
            if (event == null) {
                throw new DatabaseAccessException("This event does not exist");
            }
            else {
                AuthTok token = aDAO.findToken(authTok);
                if (!event.getUserName().equals(token.getUsername())) {
                    throw new DatabaseAccessException("The requested event does not belong to this user.");
                }
                else {
                    result = new EventIDResult(event.getEventID(), event.getUserName(), event.getPersonID(), event.getLat(),
                            event.getLong(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
                }
            }
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            result = new EventIDResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
