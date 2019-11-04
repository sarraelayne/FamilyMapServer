package Services;

import DAO.Database;
import DAO.DatabaseAccessException;
import Results.ClearResult;

/**
 * clears database of all info
 */
public class ClearService {

    public ClearResult clear() throws Exception {
        Database db = new Database();
        ClearResult result;
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            result = new ClearResult("Clear succeeded.");
        }
        catch (DatabaseAccessException e) {
            result = new ClearResult("Error: " + e.getMessage());
            db.closeConnection(false);
        }
        return result;
    }
}
