package by.mainservice.common.core.entity.converter;

import by.mainservice.common.enums.EnumConverter;
import by.mainservice.common.parameters.IdParameter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Converter
public class BaseEnumByIdConverter<T extends Enum<T> & IdParameter>
        implements AttributeConverter<T, Integer> {

    private final Class<T> enumClass;

    @Override
    public Integer convertToDatabaseColumn(final T enumType) {
        return enumType.getId();
    }

    @Override
    public T convertToEntityAttribute(final Integer id) {
        return id == null ? null : EnumConverter.getById(id, enumClass);
    }
}
