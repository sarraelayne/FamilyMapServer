package Services;

import DAO.AuthTokDAO;
import DAO.Database;
import DAO.DatabaseAccessException;
import DAO.UsersDAO;
import Model.AuthTok;
import Model.User;
import Requests.LoginRequest;
import Results.LoginResult;

import java.sql.Connection;
import java.util.UUID;

/**
 *Performs login Operations
 */
public class LoginService {
    public LoginService() {}

    /**
     * Logs in a valid user
     * @param login the user's request to log in
     * @return result
     */
    public LoginResult login(LoginRequest login) throws Exception {
        Database db = new Database();
        LoginResult s;
        String personID;
        try {
            Connection conn = db.openConnection();

            UsersDAO uDAO = new UsersDAO(conn);
            User currUser = uDAO.readUser(login.getUsername());
            if (currUser == null)
            {
                throw new DatabaseAccessException("User does not exist. Please register.");
            }
            if (!currUser.getPassword().equals(login.getPassword()))
            {
                throw new DatabaseAccessException("Invalid credentials.");
            }
            String newAuthToken = UUID.randomUUID().toString();
            personID = currUser.getPersonID();
            AuthTok authToken = new AuthTok(newAuthToken, login.getUsername());
            AuthTokDAO aDAO = new AuthTokDAO(conn);
            aDAO.addToken(authToken);

            s = new LoginResult(authToken.getToken(), authToken.getUsername(), personID);

            db.closeConnection(true);
        }
        catch (DatabaseAccessException e)
        {
            s = new LoginResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return s;
    }
}
