package by.mainservice.modules.user.core.repository;

import by.mainservice.modules.user.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByLogin(String login);

    Optional<User> getUserById(Integer userId);

}
