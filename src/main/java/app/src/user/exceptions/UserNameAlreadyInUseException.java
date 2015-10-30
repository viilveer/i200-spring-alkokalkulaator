package app.src.user.exceptions;

/**
 * Created by Mihkel on 27.10.2015.
 */
public class UserNameAlreadyInUseException extends Exception {
    public UserNameAlreadyInUseException(String username) {
        super("The username '" + username + "' is already in use.");
    }
}
