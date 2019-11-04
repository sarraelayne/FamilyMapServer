package DAO;

import Model.User;

import java.sql.*;

/**
 * communicates with Users Table in database
 */
public class UsersDAO {
    private final Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws DatabaseAccessException { //FILL
        String sql = "INSERT INTO User (userName, password, email, firstName, lastName, gender, personID)" +
                " VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException(e.getMessage());
        }
    }
    public User findUser(String personID) throws DatabaseAccessException { //QUERY
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("userName"), rs.getString("password"), rs. getString("email"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error encountered while finding User in database");
        }
        return null;
    }
    public User readUser(String userName) throws DatabaseAccessException {
        try {
            String sql = "select * from User where userName = '" + userName + "'";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            User temp = null;
            while (rs.next())
            {
                temp = new User("", "", "", "", "", "", "");
                temp.setUsername(rs.getString("userName"));
                temp.setPassword(rs.getString("password"));
                temp.setEmail(rs.getString("email"));
                temp.setFirstName(rs.getString("firstName"));
                temp.setLastName(rs.getString("lastName"));
                temp.setGender(rs.getString("gender"));
                temp.setPersonID(rs.getString("personID"));
            }

            stmt.close();
            rs.close();

            return temp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DatabaseAccessException("User Read failed");
        }
    }
    public void removeUser(String personID) throws DatabaseAccessException{ //EMPTY
        String sql = "DELETE FROM User WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("removeUser failed", e);
        }
    }
    public boolean containsUser(String personID) throws DatabaseAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                stmt.close();
                connection.close();
                return true;
            } else {
                stmt.close();
                connection.close();
                return false;
            }
        }
        catch (SQLException e){
            throw new DatabaseAccessException("error in containsPerson", e);
        }
    }
}
