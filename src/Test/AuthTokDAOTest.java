package Test;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import Model.AuthTok;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokDAOTest extends TestParent {
    private Database db;
    private AuthTok bestTok;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestTok = new AuthTok("authTok1234", "mayjune");
        super.setUp(db);
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void insertPass() throws DatabaseAccessException {
        AuthTok tokTest = null;
        try {
            Connection conn = db.openConnection();
            AuthTokDAO aDAO = new AuthTokDAO(conn);
            aDAO.addToken(bestTok);
            tokTest = aDAO.findToken(bestTok.getToken());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(tokTest);
        assertEquals(bestTok, tokTest);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            AuthTokDAO atDAO = new AuthTokDAO(conn);
            atDAO.addToken(bestTok);
            atDAO.addToken(bestTok);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        AuthTok tokTest = bestTok;
        tokTest = assertNulltokTestSetup(tokTest);
        assertNull(tokTest);
    }

    @Test
    public void queryPass() throws  DatabaseAccessException {
        AuthTok tokTest = null;
        try {
            Connection conn = db.openConnection();
            AuthTokDAO atDAO = new AuthTokDAO(conn);
            atDAO.addToken(bestTok);
            tokTest = atDAO.findToken(bestTok.getToken());
            assertNotNull(tokTest);
            assertEquals(bestTok, tokTest);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
    }
    @Test
    public void queryFail() throws DatabaseAccessException {
        AuthTok tokTest;
        tokTest = assertNulltokTestSetup(null);
        assertNull(tokTest);
    }
    @Test
    public void clearPass() throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            AuthTokDAO aDAO = new AuthTokDAO(conn);
            aDAO.deleteToken(bestTok.getToken());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        AuthTok tokTest = bestTok;
        tokTest = assertNulltokTestSetup(tokTest);
        assertNull(tokTest);
    }

    private AuthTok assertNulltokTestSetup(AuthTok tokTest) throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            AuthTokDAO atDAO = new AuthTokDAO(conn);
            tokTest = atDAO.findToken(bestTok.getToken());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        return tokTest;
    }
}
