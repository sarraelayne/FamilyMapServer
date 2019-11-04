package DAO;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * communicates with Person Table in database
 */
public class PersonDAO {
    private final Connection connection;
    public PersonDAO(Connection connection) {
        this.connection = connection;
    }
    public void addPerson(Person person) throws DatabaseAccessException { //FILL
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, motherID, fatherID, spouseID) " +
                " VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1,person.getPersonID());
            stmt.setString(2, person.getUserName());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5,person.getGender());
            stmt.setString(6, person.getMomID());
            stmt.setString(7, person.getDadID());
            stmt.setString(8,person.getSpouseID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException(e.getMessage());
        }
    }
    public void clear() throws DatabaseAccessException { //EMPTY

    }
    public Person findByID(String personID) throws DatabaseAccessException { //QUERY
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("motherID"), rs.getString("fatherID"), rs.getString("spouseID"));
                return person;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error encountered while finding User in database");
        }
        return null;
    }
    public void removePerson(String personID) throws DatabaseAccessException{ //EMPTY
        String sql = "DELETE FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("removePerson failed", e);
        }
    }
    public boolean containsPerson(String personID) throws DatabaseAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
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

    public ArrayList<Person> getFamily(String userName) throws DatabaseAccessException {
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("motherID"), rs.getString("fatherID"), rs.getString("spouseID"));
                people.add(person);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("Error while finding family for user.");
        }
        return people;
    }
}
