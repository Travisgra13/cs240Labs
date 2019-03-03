package Services;

import Model.Person;
import Requests.PersonIDRequest;
import Requests.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * This service executes all Daos required to do Person
 */
public class PersonService {
    private Database db;

    public PersonService(Database db) {
        this.db = db;
    }
    /**
     * This gets all the family members of the user
     * @param personRequest the request body
     * @return response body
     */
    public PersonResult person(PersonRequest personRequest) throws DataAccessException {
        ArrayList<Person> familyMembers = null;
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            familyMembers = uDao.ReturnAllFamilyMembers(personRequest.getUserName());
            return new PersonResult(familyMembers, true);
        }catch(DataAccessException e) {
            db.closeConnection(false);
            return new PersonResult(null, false);
        }
    }

    public PersonIDResult personID(PersonIDRequest personIDRequest) throws DataAccessException {
        Person myPerson = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            myPerson = pDao.QueryPerson(personIDRequest.getPersonID());
            if (myPerson == null) {
                throw new DataAccessException("Null person object");
            }
            else {
                return new PersonIDResult(myPerson.getDescendant(), myPerson.getPersonID(), myPerson.getFirstName(), myPerson.getLastName(), myPerson.getGender(), myPerson.getFather(),
                        myPerson.getMother(), myPerson.getSpouse(), true);
            }
        }catch(DataAccessException e) {
            db.closeConnection(false);
            return new PersonIDResult(null, null, null, null, null, null,null,null, false);
        }
    }
}
