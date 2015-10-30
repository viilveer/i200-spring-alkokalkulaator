package app.src.repositories.user;

import app.models.User;
import app.src.user.exceptions.UserNameAlreadyInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mihkel on 27.10.2015.
 */
@Repository
public class UserRepositoryCustom implements UserRepositoryInterface {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createAccount(User user) throws UserNameAlreadyInUseException {
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new UserNameAlreadyInUseException(user.getEmail());
        }
    }
}
