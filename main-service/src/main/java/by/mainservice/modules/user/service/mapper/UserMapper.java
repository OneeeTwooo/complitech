package by.mainservice.modules.user.service.mapper;

import by.mainservice.configuration.BaseMapperConfig;
import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = BaseMapperConfig.class, componentModel = "spring")
public interface UserMapper {

    UserResponseDto mapEntityToUserResponseDto(User user);

    List<UserResponseDto> mapEntitiesToUserResponseDtos(List<User> user);

    @Mapping(target = "password", source = "passwordEncoded")
    User createUser(UserRequestDto userRequestDto, String passwordEncoded);

    UserIdResponseDto mapEntityToUserIdResponseDto(Integer id);

    @Mapping(target = "password", source = "passwordEncoded")
    User updateUser(@MappingTarget User user, UserRequestDto userRequestDto, String passwordEncoded);

}
