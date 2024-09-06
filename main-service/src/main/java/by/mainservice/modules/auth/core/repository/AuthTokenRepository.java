package by.mainservice.modules.auth.core.repository;

import by.mainservice.modules.auth.core.entity.AuthToken;
import by.mainservice.modules.user.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {

    Optional<AuthToken> findByAccessToken(String token);

    List<AuthToken> findAllByUserAndIsExpiredFalseAndIsRevokedFalse(User user);

}
