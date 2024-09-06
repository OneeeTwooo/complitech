package by.mainservice.modules.auth.service.impl;

import by.mainservice.common.exception.AuthException;
import by.mainservice.modules.auth.api.dto.request.AuthRequestDto;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;
import by.mainservice.modules.auth.core.entity.AuthToken;
import by.mainservice.modules.auth.core.repository.AuthTokenRepository;
import by.mainservice.modules.auth.service.AuthTokenService;
import by.mainservice.modules.user.core.entity.User;
import by.mainservice.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto login(final AuthRequestDto authRequestDto) {
        final var user = userService.findUserByLogin(authRequestDto.username());

        if (!passwordEncoder.matches(authRequestDto.password(), user.getPassword())) {
            throw new AuthException("Не авторизован", HttpStatus.UNAUTHORIZED.value());
        }

        final var accessToken = jwtService.generateToken(user);
        final var refreshToken = jwtService.generateRefreshToken(user);

        final var token = AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .isExpired(false)
                .isRevoked(false)
                .refreshTokenExpiry(jwtService.extractExpiration(refreshToken))
                .build();

        tokenRepository.saveAndFlush(token);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public Optional<AuthToken> findByRefreshToken(final String refreshToken) {
        return tokenRepository.findByAccessToken(refreshToken);
    }

    public boolean isRefreshTokenValid(final String refreshToken) {
        return jwtService.validateToken(refreshToken) &&
                tokenRepository.findByAccessToken(refreshToken)
                        .filter(t -> !t.getIsRevoked() && t.getRefreshTokenExpiry().isAfter(OffsetDateTime.now()))
                        .isPresent();
    }

    public void revokeToken(final String token) {
        tokenRepository.findByAccessToken(token).ifPresent(t -> {
            t.setIsRevoked(true);
            tokenRepository.save(t);
        })
        ;
    }

    public void revokeAllTokensForUser(final User user) {
        var tokens = tokenRepository.findAllByUserAndIsExpiredFalseAndIsRevokedFalse(user);
        tokens.forEach(t -> {
            t.setIsRevoked(true);
            tokenRepository.save(t);
        });
    }

}
