package Test;

import DAO.Database;
import DAO.UsersDAO;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Services.RegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest extends TestParent {
    private Database db;
    private RegisterRequest r;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        super.setUp(db);
        r = new RegisterRequest("fred", "fred123", "email@byu.edu", "Fred", "Andrews", "m");
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void registerPass() throws Exception {
        RegisterResult res = new RegisterService().register(r);

        db = new Database();
        Connection conn = db.openConnection();

        String personID = (new UsersDAO(conn).readUser("fred").getPersonID());

        assertEquals(personID, res.getPersonID());
        assertEquals("fred", res.getUserName());

        db.closeConnection(false);
    }
    @Test
    public void RegisterTwice() throws Exception {
        RegisterResult res = new RegisterService().register(r);
        RegisterResult result = new RegisterService().register(r);

        db = new Database();
        Connection conn = db.openConnection();

        String personID = (new UsersDAO(conn).readUser("fred").getPersonID());

        assertEquals(personID, res.getPersonID());
        assertEquals("fred", res.getUserName());
        assertEquals("Error: User already exists", result.getMsg());

        db.closeConnection(false);
    }
    @Test
    public void registerFail() throws Exception {
        assertEquals("[SQLITE_CONSTRAINT]  Abort due to constraint " +
                "violation (column username is not unique)", new RegisterService().register(r).getMsg());
    }
}
