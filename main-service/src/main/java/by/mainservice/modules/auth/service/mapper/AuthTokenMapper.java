package by.mainservice.modules.auth.service.mapper;

import by.mainservice.configuration.BaseMapperConfig;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;
import by.mainservice.modules.auth.core.entity.AuthToken;
import by.mainservice.modules.user.core.entity.User;
import org.mapstruct.Mapper;

import java.time.OffsetDateTime;

@Mapper(config = BaseMapperConfig.class)
public interface AuthTokenMapper {

    AuthToken create(String accessToken,
                     String refreshToken,
                     User user,
                     OffsetDateTime offsetDateTime,
                     Boolean isRevoked,
                     Boolean isExpired
    );

    AuthResponseDto mapEntityToAuthResponseDto(AuthToken authToken);
}
