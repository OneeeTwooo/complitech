package by.mainservice.common.enums;

import by.mainservice.common.parameters.IdParameter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class EnumConverter {

    public static <T extends Enum<T> & IdParameter> T getById(
            final Integer id, final Class<T> enumClass) throws EnumConverterException {
        return getByParameter(id, enumClass, T::getId);
    }

    private static <T extends Enum<T>, V> T getByParameter(
            final V value, final Class<T> enumClass, final Function<T, V> valueMapper)
            throws EnumConverterException {
        return value == null
                ? null
                : Stream.of(enumClass.getEnumConstants())
                .filter(enumType -> valueMapper.apply(enumType).equals(value))
                .findFirst()
                .orElseThrow(
                        () ->
                                new EnumConverterException(
                                        "Failed to convert value [%s] to enum [%s]".formatted(value, enumClass)));
    }

}
