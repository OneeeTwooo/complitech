package by.mainservice.modules.user.core.entity;

import by.mainservice.common.parameters.IdParameter;
import by.mainservice.common.parameters.NameParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType implements IdParameter, NameParameter {
    MAN(1, "мужской"),
    WOMEN(2, "женский"),
    UNDEFINED(3, "не задано");

    private final Integer id;
    private final String name;
}
