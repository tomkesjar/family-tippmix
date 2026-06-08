package kt.tippmix.repository;

import kt.tippmix.model.Tip;
import kt.tippmix.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySecretName(String secretName);
}
