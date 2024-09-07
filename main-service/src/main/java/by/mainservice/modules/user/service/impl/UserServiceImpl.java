package by.mainservice.modules.user.service.impl;

import by.mainservice.common.date.DateTimeService;
import by.mainservice.common.exception.PasswordValidationException;
import by.mainservice.common.exception.ValueNotFoundException;
import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.core.entity.User;
import by.mainservice.modules.user.core.repository.UserRepository;
import by.mainservice.modules.user.service.UserService;
import by.mainservice.modules.user.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final DateTimeService dateTimeService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> getAllUsers() {
        final var allUsers = userRepository.findAll();

        return userMapper.mapEntitiesToUserResponseDtos(allUsers);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getUserById(final Integer userId) {
        final var user = userRepository.getUserById(userId)
                .orElseThrow(() -> new ValueNotFoundException("Сотрудник с id [%s] не найден".formatted(userId), 201));

        return userMapper.mapEntityToUserResponseDto(user);
    }

    @Transactional
    @Override
    public UserIdResponseDto createUser(final UserRequestDto userRequestDto) {
        validatePassword(userRequestDto.password());
        final var passwordEncoded = passwordEncoder.encode(userRequestDto.password());

        final var user = userMapper.createUser(userRequestDto, passwordEncoded);

        userRepository.saveAndFlush(user);

        return userMapper.mapEntityToUserIdResponseDto(user.getId());
    }

    @Transactional
    @Override
    public UserIdResponseDto updateUser(final Integer userId, final UserRequestDto userRequestDto) {
        validatePassword(userRequestDto.password());

        final var user = userRepository.getUserById(userId)
                .orElseThrow(() -> new ValueNotFoundException("Сотрудник с id [%s] не найден".formatted(userId), 201));

        final var passwordEncoded = passwordEncoder.encode(userRequestDto.password());

        final var updatedUser = userMapper.updateUser(user, userRequestDto, passwordEncoded);

        updatedUser.setModifiedBy("system");
        updatedUser.setUpdatedAt(dateTimeService.nowOffsetDateTime());

        userRepository.saveAndFlush(updatedUser);

        return userMapper.mapEntityToUserIdResponseDto(updatedUser.getId());
    }

    @Transactional
    @Override
    public void deleteUserById(final Integer userId) {
        final var user = userRepository.getUserById(userId)
                .orElseThrow(() -> new ValueNotFoundException(
                        "Сотрудник с id [%s] не найден".formatted(userId), NOT_FOUND.value()));

        userRepository.delete(user);
    }


    @Transactional(readOnly = true)
    @Override
    public User findUserByLogin(final String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new ValueNotFoundException(
                        "Сотрудник с логином [%s] не найден".formatted(login), NOT_FOUND.value()));
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void deleteUsersByRange(Integer startId, Integer endId) {
        userRepository.deleteUsersByRange(startId, endId);
    }

    private void validatePassword(final String password) {
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new PasswordValidationException(
                    "Пароль должен содержать хотя бы один специальный символ", INTERNAL_SERVER_ERROR.value());
        }

        if (!password.matches(".*\\d.*\\d.*\\d.*")) {
            throw new PasswordValidationException(
                    "Пароль должен содержать хотя бы три цифры", INTERNAL_SERVER_ERROR.value());
        }
    }
}
