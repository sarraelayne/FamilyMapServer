package Requests;

/**
 * Request to log in user
 */

public class LoginRequest {
    /**
     * Sets all of the login info
     */
    private String userName;
    private String password;

    public LoginRequest() {}
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
}
