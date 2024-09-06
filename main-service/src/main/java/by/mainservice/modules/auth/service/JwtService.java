package by.mainservice.modules.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    Boolean validateToken(String token);

    String extractUsername(String token);

    String extractUserRole(String token);

    OffsetDateTime extractExpiration(String token);

}
