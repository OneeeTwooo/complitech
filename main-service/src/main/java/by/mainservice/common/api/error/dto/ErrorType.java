package by.mainservice.common.api.error.dto;

import by.mainservice.common.parameters.NameParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType implements NameParameter {
    SYSTEM("Системная ошибка"),

    VALIDATION("Ошибка валидации"),

    PROCESSING("Ошибка обработки данных"),

    SECURITY("Ошибка доступа");

    private final String name;
}
