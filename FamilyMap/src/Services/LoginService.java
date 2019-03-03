package Services;

import Model.AuthToken;
import Model.User;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import dataAccess.AuthTokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This service executes all Daos required to do Login
 */
public class LoginService {
    /**
     * This logins the user
     * @param loginRequest the request body
     * @return response body
     */
    private Database db;

    public LoginService(Database db) {
        this.db = db;
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{
        String proposedUserName = loginRequest.getUserName();
        String proposedPassword = loginRequest.getPassword();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            AuthTokenDao aDao = new AuthTokenDao(conn);
            User myUser = uDao.QueryUser(proposedUserName);
            if(myUser == null) {
                db.closeConnection(false);
                return new LoginResult(null, proposedUserName, null, false);
            }
            else if (!myUser.getPassword().equals(proposedPassword)) {
                db.closeConnection(false);
                return new LoginResult(null, proposedUserName, null, false);
            }
            else {
                String token = aDao.AddAuthToken(myUser.getUserName());
                db.closeConnection(true);
                return new LoginResult(token, myUser.getUserName(), myUser.getPersonID(), true);
            }
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        return null;
    }
}
