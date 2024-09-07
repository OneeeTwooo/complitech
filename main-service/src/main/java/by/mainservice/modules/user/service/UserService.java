package by.mainservice.modules.user.service;

import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserPageInfoResponseDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.core.entity.User;

public interface UserService {

    UserPageInfoResponseDto getAllUsers(Integer page, Integer limit, String sort);

    UserResponseDto getUserById(Integer userId);

    UserIdResponseDto createUser(UserRequestDto userRequestDto);

    UserIdResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    void deleteUserById(Integer userId);

    User findUserByLogin(String login);

    User getCurrentUser();

    void deleteUsersByRange(Integer startId, Integer endId);
}
