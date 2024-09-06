package by.mainservice.modules.user.api.dto.request;

import by.mainservice.modules.user.api.dto.deserializer.GenderTypeByIdDeserializer;
import by.mainservice.modules.user.core.entity.GenderType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotBlank(message = "Логин не заполнен")
        @Size(max = 50, message = "Недопустимое количество символов")
        String login,

        @Size(max = 20, min = 7, message = "Недопустимое количество символов")
        @NotBlank(message = "Пароль не заполнен")
        String password,

        @Size(max = 256, message = "Недопустимое количество символов")
        @NotBlank(message = "Пароль не заполнен")
        String fullName,

        @JsonDeserialize(using = GenderTypeByIdDeserializer.class)
        GenderType genderType
) {
}
