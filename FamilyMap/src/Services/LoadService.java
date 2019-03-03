package Services;

import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Result.LoadResult;
import dataAccess.*;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * This service executes all Daos required to do Load
 */
public class LoadService {
    private Database db;
    private User[] myUsers;
    private Person[] myPersons;
    private Event[] myEvents;

    public LoadService(Database db) {
        this.db = db;
    }
    /**
     * This loads information into the server
     * @param loadRequest the request body
     * @return response body
     */
    public LoadResult load(LoadRequest loadRequest) throws DataAccessException{
        myUsers = loadRequest.getMyUsers();
        myPersons = loadRequest.getMyPersons();
        myEvents = loadRequest.getMyEvents();
        try {
            if (myUsers == null || myPersons == null || myEvents == null) {
                throw new DataAccessException("Missing Values");
            }
            if (myEvents.length == 0 || myPersons.length == 0 || myUsers.length == 0) {
                throw new DataAccessException("Missing Values");
            }
            db.clearTables();
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            UserDao uDao = new UserDao(conn);
            EventDao eDao = new EventDao(conn);
            if (!processObjects(pDao, uDao, eDao)) {
                throw new DataAccessException("Error after Processing Objects");
            }
            db.closeConnection(true);
            return new LoadResult(true, myUsers.length, myPersons.length, myEvents.length);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            return new LoadResult(false, 0, 0, 0);
        }
    }
    private boolean processObjects(PersonDao pDao, UserDao uDao, EventDao eDao) throws DataAccessException {
        try {
            for (int i = 0; i < myUsers.length; i++) {
                uDao.AddUser(myUsers[i]);
            }

            for (int j = 0; j < myPersons.length; j++) {
                pDao.AddPerson(myPersons[j]);
            }

            for (int k = 0; k < myEvents.length; k++) {
                eDao.AddEvent(myEvents[k]);
            }
        }catch (DataAccessException e) {
            db.closeConnection(false);
            return false;
        }
        return true;
    }

}
