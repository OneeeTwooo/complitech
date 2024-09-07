package by.mainservice.modules.user.service.impl;

import by.mainservice.common.date.DateTimeService;
import by.mainservice.common.exception.PasswordValidationException;
import by.mainservice.common.exception.ValueNotFoundException;
import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserPageInfoResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.core.entity.User;
import by.mainservice.modules.user.core.repository.UserRepository;
import by.mainservice.modules.user.service.UserService;
import by.mainservice.modules.user.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private static final int PAGE_OFFSET = 1;

    private final PasswordEncoder passwordEncoder;
    private final DateTimeService dateTimeService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserPageInfoResponseDto getAllUsers(final Integer page, final Integer limit, final String sort) {

        final var pageRequest = getPageRequest(page, limit, sort);

        final var allUsers = userRepository.findAll(pageRequest);

        final var userInfoResponseDto =
                allUsers.getContent().stream()
                        .map(userMapper::mapEntityToUserResponseDto)
                        .toList();

        return UserPageInfoResponseDto.builder()
                .page(allUsers.getNumber() + PAGE_OFFSET)
                .total(allUsers.getTotalPages())
                .limit(allUsers.getSize())
                .items(userInfoResponseDto)
                .build();
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

    private static PageRequest getPageRequest(final Integer page, final Integer limit, final String sort) {
        final var finalSort = ASC.name().equalsIgnoreCase(sort) ? ASC : DESC;
        final var finalLimit = page == -1 ? Integer.MAX_VALUE : limit;
        final var finalPage = page == -1 ? 0 : page - PAGE_OFFSET;

        return PageRequest.of(finalPage, finalLimit, finalSort, "id");
    }
}
