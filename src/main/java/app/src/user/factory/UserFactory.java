package app.src.user.factory;

import app.models.User;
import app.src.forms.SignupForm;
import app.src.repositories.user.UserRepositoryInterface;
import app.src.user.exceptions.UserNameAlreadyInUseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Mihkel on 27.10.2015.
 */
public class UserFactory {
    // internal helpers

    public static User createFromSignupForm(UserRepositoryInterface userRepositoryCustom, SignupForm form) throws UserNameAlreadyInUseException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        User account = new User(form.getEmail(), form.getName(), LocalDateTime.now().format(formatter));
        userRepositoryCustom.createAccount(account);
        return account;
    }
}
