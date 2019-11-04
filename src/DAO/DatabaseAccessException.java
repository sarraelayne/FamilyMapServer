package DAO;

public class DatabaseAccessException extends Exception { //DONE???
        public DatabaseAccessException(String message) {
            super(message);
        }
        DatabaseAccessException(String s, Throwable throwable) {
            super(s, throwable);
        }

}
