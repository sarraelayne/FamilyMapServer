package Model;

/**
 * keeps track of all of the authTokens
 */

public class AuthTok {
    String authToken;
    String userName;

    public AuthTok(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }
    public String getToken() {
        return authToken;
    }
    public void setToken(String token) {}
    public String getUsername() {
        return userName;
    }
    public void setUsername(String userName) {}

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof AuthTok) {
            AuthTok oAT = (AuthTok) o;
            return oAT.getToken().equals(getToken()) &&
                    oAT.getUsername().equals(getUsername());
        }
        else {
            return false;
        }
    }
}
