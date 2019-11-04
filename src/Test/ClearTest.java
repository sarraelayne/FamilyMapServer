package Test;

import DAO.Database;
import static org.junit.jupiter.api.Assertions.*;

import Results.ClearResult;
import Results.Result;
import Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

class ClearTest extends TestParent {
    private Database db;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        super.setUp(db);
    }

    @AfterEach
    void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    void clearPass() throws Exception {
        boolean foundTok = false;
        boolean foundUser = false;
        boolean foundPerson = false;
        boolean foundEvent = false;

        try {
            Connection conn = db.openConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT into AuthTok values (\"authToken\", \"userName\")");
            stmt.executeUpdate("INSERT into Event values (\"eventID\", \"associatedUsername\", \"personID\", " +
                    "\"latitude\", \"longitude\", \"country\", \"city\", \"eventType\", \"year\")");
            stmt.executeUpdate("INSERT into Person values (\"personID\", \"associatedUsername\", \"firstName\", " +
                    "\"lastName\", \"gender\", \"motherID\", \"fatherID\", \"spouseID\")");
            stmt.executeUpdate("INSERT into User values (\"userName\", \"password\", \"email\", \"firstName\", " +
                    "\"lastName\", \"gender\", \"personID\")");
            db.closeConnection(true);
        }
        catch (Exception e) {
            System.out.println("Error1: " + e.getMessage());
            db.closeConnection(false);
        }

        ClearResult result = new ClearService().clear();

        try {
            Connection conn = db.openConnection();
            String sql = "SELECT count(*) as rows FROM User";
            PreparedStatement stmt = conn.prepareStatement(sql);
            foundUser = stmt.executeQuery().getString("rows").equals("0");
            sql = "SELECT count(*) as rows FROM Event";
            stmt = conn.prepareStatement(sql);
            foundEvent = stmt.executeQuery().getString("rows").equals("0");
            sql = "SELECT count(*) as rows FROM Person";
            stmt = conn.prepareStatement(sql);
            foundPerson = stmt.executeQuery().getString("rows").equals("0");
            sql = "SELECT count(*) as rows FROM AuthTok";
            stmt = conn.prepareStatement(sql);
            foundTok = stmt.executeQuery().getString("rows").equals("0");
            stmt.close();
            db.closeConnection(true);
        } catch (Exception e) {
            System.out.println("Error2: " + e.getMessage());
            db.closeConnection(false);
        }

        assertTrue(foundEvent);
        assertTrue(foundUser);
        assertTrue(foundPerson);
        assertTrue(foundTok);
        assertEquals("Clear succeeded.", result.getMsg());
    }

    @Test
    void clearFail() throws Exception {
        Result result = new ClearService().clear();
        assertNotEquals("Clear succeeded.", result.getMsg());
    }
}
