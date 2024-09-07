package by.mainservice.modules.user.service;

import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.core.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Integer userId);

    UserIdResponseDto createUser(UserRequestDto userRequestDto);

    UserIdResponseDto updateUser(Integer userId, UserRequestDto userRequestDto);

    void deleteUserById(Integer userId);
    
    User findUserByLogin(String login);

    User getCurrentUser();

    void deleteUsersByRange(Integer startId, Integer endId);
}
