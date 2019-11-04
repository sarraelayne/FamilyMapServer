package Test;

import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.PersonDAO;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class personDAOtest extends TestParent {
    private Database db;
    private Person bestPerson;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestPerson = new Person("123ID", "catsrule", "samuel", "jones",
                "m", "HiMom", "ImDad", "spruce");
        super.setUp(db);
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void insertPass() throws Exception {
        Person personTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.addPerson(bestPerson);
            personTest = pDAO.findByID(bestPerson.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(personTest); //CHECK IF FIND GOT ANYTHING AT ALL
        assertEquals(bestPerson, personTest); //CHECK IF THE TEST IS WHAT WE THOUGHT IT WOULD BE
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.addPerson(bestPerson);
            pDAO.addPerson(bestPerson);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        Person personTest = bestPerson;
        personTest = assertNullpersonTestSetup(personTest);
        assertNull(personTest);
    }

    @Test
    public void queryPass() throws DatabaseAccessException {
        Person personTest = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.addPerson(personTest);
            personTest = pDAO.findByID(bestPerson.getPersonID());
            assertNotNull(personTest);
            assertEquals(personTest, bestPerson);
            db.closeConnection(true);
        } catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    public void queryFail() throws DatabaseAccessException {
        Person personTest = bestPerson;
        personTest = assertNullpersonTestSetup(personTest);
        assertNull(personTest);
    }

    @Test
    public void clearPass() throws DatabaseAccessException {
        try {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.addPerson(bestPerson);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }

        Person personDelTest = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDAO = new PersonDAO(conn);
            pDAO.removePerson(bestPerson.getPersonID());
            personDelTest = pDAO.findByID(bestPerson.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        assertNull(personDelTest);
    }

    private Person assertNullpersonTestSetup(Person personTest) throws DatabaseAccessException {
        try {
            Connection connection = db.openConnection();
            PersonDAO pDAO = new PersonDAO(connection);
            personTest = pDAO.findByID(bestPerson.getPersonID());
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
        }
        return personTest;
    }
}