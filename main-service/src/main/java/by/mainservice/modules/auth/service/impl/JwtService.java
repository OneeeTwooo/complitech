package by.mainservice.modules.auth.service.impl;

import by.mainservice.modules.user.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements by.mainservice.modules.auth.service.JwtService {

    private static final String ROLE = "role";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessTokenExpiration}")
    private long jwtAccessTokenExpirationMs;

    @Value("${jwt.refreshTokenExpiration}")
    private long jwtRefreshTokenExpirationMs;

    @Override
    public String generateToken(final UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtAccessTokenExpirationMs);
    }

    @Override
    public String generateRefreshToken(final UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtRefreshTokenExpirationMs);
    }

    @Override
    public Boolean validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSignInKey())
                    .build()
                    .parse(token);
            return true;
        } catch (final JwtException e) {
            return false;
        }
    }

    @Override
    public String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public String extractUserRole(final String token) {
        return extractAllClaims(token).get(ROLE, String.class);
    }

    @Override
    public OffsetDateTime extractExpiration(final String token) {
        final var expiration = extractAllClaims(token).getExpiration();
        return OffsetDateTime.ofInstant(expiration.toInstant(), OffsetDateTime.now().getOffset());
    }

    private String buildToken(
            final Map<String, Object> extraClaims,
            final UserDetails userDetails,
            final long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .claim(ROLE, ((User) userDetails).getUserRole().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).isBefore(OffsetDateTime.now());
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private Key getSignInKey() {
        final var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
