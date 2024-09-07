package by.mainservice.modules.user.core.repository;

import by.mainservice.modules.user.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByLogin(String login);

    Optional<User> getUserById(Integer userId);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    select complitech.delete_users_in_range(:startId, :endId)
                    """
    )
    void deleteUsersByRange(@Param("startId") Integer startId, @Param("endId") Integer endId);
}
