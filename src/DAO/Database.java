package DAO;

import java.sql.*;

public class Database {
    private Connection connect;

    public Connection openConnection() throws DatabaseAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.sqlite";
            connect = DriverManager.getConnection(CONNECTION_URL);
            connect.setAutoCommit(false);
            return connect;
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("openConnection failed");
        }
    }
    public void closeConnection(boolean commit) throws DatabaseAccessException {
        try {
            if (commit) {
                connect.commit();
            }
            else {
                connect.rollback();
            }
            connect.close();
            connect = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseAccessException("closeConnection failed");
        }
    }
    public void createTables() throws DatabaseAccessException, SQLException {
        PreparedStatement stmt = null;
        try {
            String one = "CREATE TABLE IF NOT EXISTS User " +
                    "(" + "userName text not null unique, " +
                    "password text not null, " +
                    "email text not null, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "personID text not null unique, " +
                    "primary key(personID)" + ")";
            String two = "CREATE TABLE IF NOT EXISTS Event " +
                    "(" + "eventID text not null unique, " +
                    "associatedUsername text not null, " +
                    "personID text not null, " +
                    "latitude numeric not null, " +
                    "longitude numeric not null, " +
                    "country text not null, " +
                    "city text not null, " +
                    "eventType text not null, " +
                    "year integer not null, " +
                    "primary key(eventID)" + ")";
            String three = "CREATE TABLE IF NOT EXISTS Person " +
                    "(" + "personID text not null unique, " +
                    "associatedUsername text not null, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "motherID text, " +
                    "fatherID text, " +
                    "spouseID text, " +
                    "primary key(personID)" + ")";
            String four = "CREATE TABLE IF NOT EXISTS AuthTok " +
                    "(" + "authToken text not null unique, " +
                    "userName text not null, " +
                    "primary key(authToken)" + ")";

            stmt = connect.prepareStatement(one);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(two);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(three);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(four);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("createTables failed", e);
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public void clearTables() throws DatabaseAccessException { //CHECK THIS BOI
        String one = "DELETE FROM User; \n";
        String two = "DELETE FROM Event; \n";
        String three = "DELETE FROM Person; \n";
        String four = "DELETE FROM AuthTok; \n";
        try (Statement stmt = connect.createStatement()){
            stmt.executeUpdate(one);
            stmt.executeUpdate(two);
            stmt.executeUpdate(three);
            stmt.executeUpdate(four);
        }
        catch (SQLException e) {
            throw new DatabaseAccessException(e.getMessage());
        }
    }
    public void deleteTables() throws DatabaseAccessException {
        PreparedStatement stmt;
        try {
            String one = "DROP TABLE IF EXISTS User; \n";
            String two = "DROP TABLE IF EXISTS Event; \n";
            String three = "DROP TABLE IF EXISTS Person; \n";
            String four = "DROP TABLE IF EXISTS AuthTok; \n";

            stmt = connect.prepareStatement(one);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(two);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(three);
            stmt.executeUpdate();
            stmt = connect.prepareStatement(four);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseAccessException("deleteTables failed", e);
        }
    }

}
