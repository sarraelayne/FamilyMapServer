package Results;
/**
 * Represents response to a request on /login
 */
public class LoginResult extends Result {
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
    public LoginResult(String message) {
        this.message = message;
    }
    public String getAuthToken() { return authToken; }
    public String getPersonID() { return personID; }
    public String getUserName() { return userName; }
}
