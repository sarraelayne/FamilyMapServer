package Services;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.PersonDAO;
import Model.AuthTok;
import Model.Person;
import Results.PersonResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * finds and returns a person or persons
 */
public class PersonService {

    private String checkToken(String authToken) throws Exception {
        Database db = new Database();
        AuthTok token;

        try {
            Connection connect = db.openConnection();
            AuthTokDAO atDAO = new AuthTokDAO(connect);
            token = atDAO.findToken(authToken);
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            db.closeConnection(false);
            throw new DatabaseAccessException("Error: " + e.getMessage());
        }
        if (token != null) {
            return token.getUsername();
        }
        return null;
    }
    public PersonResult getPeople(String authToken) throws Exception {
        PersonResult result;
        String username = checkToken(authToken);
        Database db = new Database();
        ArrayList<Person> persons;

        try {
            if(username == null) {
                throw new DatabaseAccessException("Invalid authorization token.");
            }
            Connection connect = db.openConnection();
            PersonDAO pDAO = new PersonDAO(connect);
            persons = pDAO.getFamily(username);
            db.closeConnection(true);
            if (persons.size() == 0) {
                throw new DatabaseAccessException("No persons for this user exist.");
            }
            else {
                Person[] people = persons.toArray(new Person[persons.size()]);
                result = new PersonResult(people);
            }
        }
        catch (DatabaseAccessException e) {
            result = new PersonResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
