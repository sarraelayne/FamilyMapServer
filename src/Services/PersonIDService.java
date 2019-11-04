package Services;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.PersonDAO;
import Model.AuthTok;
import Model.Person;
import Results.PersonIDResult;

import java.sql.Connection;

public class PersonIDService { //REQUIRES AUTHTOK

    public PersonIDResult getPerson(String personID, String authToken) throws DatabaseAccessException {
        Database db = new Database();
        PersonIDResult result;
        Person person;
        AuthTok token;

        try {
            Connection connect = db.openConnection();
            PersonDAO pDAO = new PersonDAO(connect);
            AuthTokDAO aDAO = new AuthTokDAO(connect);
            person = pDAO.findByID(personID);
            if(person == null) {
                throw new DatabaseAccessException("This person does not exist.");
            }
            else {
                token = aDAO.findToken(authToken);
                if (!person.getUserName().equals(token.getUsername())) {
                    throw new DatabaseAccessException("The requested person is not related to this user.");
                }
                else {
                    result = new PersonIDResult(person.getPersonID(), person.getUserName(), person.getFirstName(),
                            person.getLastName(), person.getGender(), person.getMomID(), person.getDadID(), person.getSpouseID());
                }
            }
            db.closeConnection(true);
        }
        catch (DatabaseAccessException e) {
            result = new PersonIDResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
