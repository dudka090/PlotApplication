package pl.myapplication.plot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

   Optional <User> findUserByLogin(String login);

   Optional<User> findUserByEmail(String email);

   User save (User user);

   boolean existsUserByLogin(String login);

   boolean existsUserByEmail(String email);

   List<User> findAll();
}
