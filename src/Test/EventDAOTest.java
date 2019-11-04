package Test;

import java.sql.Connection;

import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.EventDAO;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventDAOTest extends TestParent {
    private Database db;
    private Event bestEvent;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        bestEvent = new Event("myEvent", "aUser", "123ID", 24.5, -30.666, "Australia",
                "Berlin" ,"bikeRace", 1999);
        super.setUp(db);
    }

    @AfterEach
    void tearDown() throws Exception{
        super.tearDown(db);
    }

    @Test
    void insertPass() throws DatabaseAccessException {
        Event eventTest = null;
        try {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.insert(bestEvent);
            eventTest = eDAO.find(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e){
            db.closeConnection(false);
        }
        assertNotNull(eventTest);
        assertEquals(bestEvent, eventTest);
    }

    @Test
    void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection connection = db.openConnection();
            EventDAO eDAO = new EventDAO(connection);
            eDAO.insert(bestEvent);
            eDAO.insert(bestEvent);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        Event eventTest = bestEvent;
        eventTest = assertNulleventTestSetup(eventTest);
        assertNull(eventTest);
    }

    @Test
    void queryPass() throws DatabaseAccessException {
        Event eventTest;
        try{
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.insert(bestEvent);
            eventTest = eDAO.find(bestEvent.getEventID());
            assertNotNull(eventTest);
            assertEquals(bestEvent, eventTest);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    void queryFail() throws DatabaseAccessException {
        Event eventTest;
        eventTest = assertNulleventTestSetup(null);
        assertNull(eventTest);
    }

    @Test
    void clearPass() throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eDAO.removeEvent(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        Event eventTest = bestEvent;
        eventTest = assertNulleventTestSetup(eventTest);
        assertNull(eventTest);
    }


    private Event assertNulleventTestSetup(Event eventTest) throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            EventDAO eDAO = new EventDAO(conn);
            eventTest = eDAO.find(bestEvent.getEventID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        return eventTest;
    }
}

