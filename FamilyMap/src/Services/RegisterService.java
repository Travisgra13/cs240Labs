package Services;

import Requests.RegisterRequest;
import Result.RegisterResult;
/**
 * This service executes all Daos required to do Register
 */
public class RegisterService {
    /**
     * This registers a new user
     * @param request the request body
     * @return response body
     */
    public RegisterResult register(RegisterRequest request) {

        //Process RegisterRequest into Model objects to pass into Daos
        //Calls the UserDao, PersonDao, etc. to create a new user account
       //FILLREQUEST to randomly generate ancestry
        //LOGIN SERVICE to login user
        //RETURN REGISTERRESULT
        return null;
    }
}
