package by.mainservice.modules.user.api.dto.response;

import by.mainservice.modules.user.api.dto.serializer.GenderTypeByNameSerializer;
import by.mainservice.modules.user.core.entity.GenderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDto(

        Integer id,

        String login,

        String fullName,

        @JsonSerialize(using = GenderTypeByNameSerializer.class)
        GenderType genderType

) {
}
