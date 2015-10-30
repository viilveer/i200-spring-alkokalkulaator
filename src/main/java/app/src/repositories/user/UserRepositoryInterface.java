package app.src.repositories.user;

import app.models.User;
import app.src.user.exceptions.UserNameAlreadyInUseException;

/**
 * Created by Mihkel on 27.10.2015.
 */
public interface UserRepositoryInterface {
    void createAccount(User user) throws UserNameAlreadyInUseException;

}
