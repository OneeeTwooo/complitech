package by.mainservice.common.enums.deserializer;


import by.mainservice.common.enums.EnumConverter;
import by.mainservice.common.parameters.IdParameter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class BaseEnumByIdDeserializer<T extends Enum<T> & IdParameter> extends StdDeserializer<T> {

    private final Class<T> enumClass;

    public BaseEnumByIdDeserializer(final Class<T> enumClass) {
        super(enumClass);
        this.enumClass = enumClass;
    }

    @Override
    public T deserialize(final JsonParser parser, final DeserializationContext context)
            throws IOException {
        final var id = (Integer) parser.getValueAsInt();
        return id == 0 ? null : EnumConverter.getById(id, enumClass);
    }
}
