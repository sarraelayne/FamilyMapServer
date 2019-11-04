package Test;

import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.UsersDAO;
import Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class userDAOtest extends TestParent {
    private Database db;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("catsrule", "thisismypassword", "samuel@aol.com",
                "samuel", "jones", "m", "123ID");
        super.setUp(db);
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void insertPass() throws DatabaseAccessException {
        User userTest = null;
        try {
            Connection conn = db.openConnection();
            UsersDAO uDAO = new UsersDAO(conn);
            uDAO.addUser(bestUser);
            userTest = uDAO.findUser(bestUser.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(userTest); //CHECK IF FIND GOT ANYTHING AT ALL
        assertEquals(bestUser, userTest); //CHECK IF THE TEST IS WHAT WE THOUGHT IT WOULD BE
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UsersDAO uDAO = new UsersDAO(conn);
            uDAO.addUser(bestUser);
            uDAO.addUser(bestUser);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        User userTest = bestUser;
        userTest = assertNulluserTestSetup(userTest);
        assertNull(userTest);
    }

    @Test
    public void queryPass() throws DatabaseAccessException {
        User userTest = null;
        try {
            Connection conn = db.openConnection();
            UsersDAO uDAO = new UsersDAO(conn);
            uDAO.addUser(bestUser);
            userTest = uDAO.findUser(bestUser.getPersonID());
            assertNotNull(userTest);
            assertEquals(bestUser, userTest);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    public void queryFail() throws DatabaseAccessException {
        User userTest;
        userTest = assertNulluserTestSetup(null);
        assertNull(userTest);
    }

    @Test
    public void clearPass() throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            UsersDAO uDAO = new UsersDAO(conn);
            uDAO.removeUser(bestUser.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        User userTest = bestUser;
        userTest = assertNulluserTestSetup(userTest);
        assertNull(userTest);
    }

    private User assertNulluserTestSetup(User userTest) throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            UsersDAO uDAO = new UsersDAO(conn);
            userTest = uDAO.findUser(bestUser.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        return userTest;
    }
}