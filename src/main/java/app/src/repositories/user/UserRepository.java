package app.src.repositories.user;

import app.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Mihkel on 10.10.2015.
 */
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
