package DAO;

import Model.AuthTok;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * communicates with AuthToken Table in database
 */
public class AuthTokDAO {
    private final Connection conn;

    public AuthTokDAO(Connection conn) {
        this.conn = conn;
    }

    public void addToken(AuthTok token) throws DatabaseAccessException {
        String sql = "INSERT INTO AuthTok (authToken, userName)" + " VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("Error encountered while adding Auth Token");
        }
    }
    public AuthTok findToken(String authToken) throws DatabaseAccessException {
        AuthTok token;
        ResultSet rs;
        String sql = "SELECT * FROM AuthTok WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthTok(rs.getString("authToken"), rs.getString("userName"));
                return token;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error while finding authToken in database");
        }
        return null;
    }
    public boolean containsToken(String authToken) throws DatabaseAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM AuthTok WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                stmt.close();
                conn.close();
                return true;
            }

            else {
                stmt.close();
                conn.close();
                return false;
            }
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("Error in containsToken", e);
        }
    }
    public void deleteToken(String authToken) throws DatabaseAccessException {
        String sql = "DELETE FROM AuthTok WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("deleteToken failed");
        }
    }
}
