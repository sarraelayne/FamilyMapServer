package Services;

import DAO.*;
import Model.AuthTok;
import Model.Person;
import Model.User;
import Requests.RegisterRequest;
import Results.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * register a user
 */
public class RegisterService {
    public RegisterService() {}
    /**
     * Registers a user & makes a family tree
     * @param register
     * @return RegisterResult
     */
    public RegisterResult register(RegisterRequest register) throws Exception {
        FillService service = new FillService();
        Database db = new Database();
        RegisterResult result;
        User user;

        try {
            Connection connection = db.openConnection();
            db.createTables();
            UsersDAO uDAO = new UsersDAO(connection);
            user = uDAO.readUser(register.getUsername());

            if (user != null && user.getUsername().equals(register.getUsername())) {
                throw new DatabaseAccessException("User already exists");
            }
            String newID = UUID.randomUUID().toString();

            Person newPerson = new Person(newID, register.getUsername(), register.getFirstName(), register.getLastName(),
                    register.getGender(), null, null, null);
            PersonDAO pDAO = new PersonDAO(connection);
            pDAO.addPerson(newPerson);
            User newUser = new User(register.getUsername(), register.getPassword(), register.getEmail(), register.getFirstName(),
                    register.getLastName(), register.getGender(), newID);
            uDAO.addUser(newUser);

            service.fill(register.getUsername(), 4);

            String newAuthToken = UUID.randomUUID().toString();
            AuthTok authTok = new AuthTok(newAuthToken, register.getUsername());
            AuthTokDAO aDAO = new AuthTokDAO(connection);
            aDAO.addToken(authTok);

            result = new RegisterResult(authTok.getToken(), register.getUsername(), newPerson.getPersonID());
            db.closeConnection(true);
        } catch (DatabaseAccessException e) {
            result = new RegisterResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
