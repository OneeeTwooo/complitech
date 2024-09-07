package by.mainservice.modules.auth.service.impl;

import by.mainservice.common.exception.AuthException;
import by.mainservice.modules.auth.api.dto.request.AuthRequestDto;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;
import by.mainservice.modules.auth.core.repository.AuthTokenRepository;
import by.mainservice.modules.auth.service.AuthTokenService;
import by.mainservice.modules.auth.service.mapper.AuthTokenMapper;
import by.mainservice.modules.user.core.entity.User;
import by.mainservice.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private static final String USER_UNAUTHORIZED_ERROR_MESSAGE = "Пользователь не авторизован";

    private final AuthTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenMapper authTokenMapper;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional
    @Override
    public AuthResponseDto login(final AuthRequestDto authRequestDto) {
        final var user = userService.findUserByLogin(authRequestDto.username());

        if (!passwordEncoder.matches(authRequestDto.password(), user.getPassword())) {
            throw new AuthException(USER_UNAUTHORIZED_ERROR_MESSAGE, HttpStatus.UNAUTHORIZED.value());
        }

        final var accessToken = jwtService.generateToken(user);
        final var refreshToken = jwtService.generateRefreshToken(user);

        final var authToken = authTokenMapper.create(
                accessToken, refreshToken, user, jwtService.extractExpiration(refreshToken));

        tokenRepository.saveAndFlush(authToken);

        return authTokenMapper.mapEntityToAuthResponseDto(authToken);
    }

    @Transactional
    @Override
    public void logout() {
        final var currentUser = userService.getCurrentUser();

        revokeAllTokensForUser(currentUser);
    }

    public void revokeAllTokensForUser(final User user) {
        var tokens = tokenRepository.findAllByUserAndIsExpiredFalseAndIsRevokedFalse(user);
        tokens.forEach(t -> {
            t.setIsRevoked(true);
            tokenRepository.save(t);
        });
    }

}
