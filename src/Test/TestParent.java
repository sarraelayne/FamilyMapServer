package Test;

import DAO.Database;

public class TestParent {
    public void setUp (Database db) throws Exception {
        db.openConnection();
        db.deleteTables();
        db.createTables();
        db.closeConnection(true);
    }
    public void tearDown(Database db) throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
}
