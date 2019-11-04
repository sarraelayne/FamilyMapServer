package Test;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.PersonDAO;
import Model.AuthTok;
import Model.Person;
import Results.PersonIDResult;
import Services.PersonIDService;
import Services.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class PersonIDTest extends TestParent {
    private Database db;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        super.setUp(db);
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void getPersonPass() throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT into AuthTok values (\"authToken\", \"username\")");
            stmt.executeUpdate("INSERT into Person values (\"personID\", \"username\", " +
                    "\"firstName\", \"lastName\", \"m\", \"mother\", \"father\", \"spouse\")");
            db.closeConnection(true);
        } catch (Exception e) {
            db.closeConnection(false);
        }

        PersonIDResult result = new PersonIDService().getPerson("personID", "authToken");

        assertNotNull(result);
        assertEquals("personID", result.getPersonID());
        assertEquals("username", result.getUserName());
        assertEquals("firstName", result.getFirstName());
        assertEquals("lastName", result.getLastName());
        assertEquals("m", result.getGender());
        assertEquals("father", result.getDadID());
        assertEquals("mother", result.getMomID());
        assertEquals("spouse", result.getSpouseID());
    }
    @Test
    public void getPersonFail() {
    }
}
