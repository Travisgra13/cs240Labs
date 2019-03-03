package Services;

import Result.ClearResult;
import dataAccess.DataAccessException;
import dataAccess.Database;

/**
 * This service executes all Daos required to clear
 */
public class ClearService {
    private Database db;

    public ClearService(Database db) {
        this.db = db;
    }
    /**
     * Clears all of database
     * @return the response body
     */
    public ClearResult clear() {
        try {
            db.clearTables();
            return new ClearResult(true);
        }catch (DataAccessException e) {
            return new ClearResult(false);
        }
    }
}
