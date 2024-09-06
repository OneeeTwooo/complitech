package by.mainservice.common.enums.serializer;

import by.mainservice.common.parameters.NameParameter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BaseEnumByNameSerializer<T extends Enum<T> & NameParameter> extends StdSerializer<T> {

    public BaseEnumByNameSerializer() {
        this(null);
    }

    private BaseEnumByNameSerializer(final Class<T> enumClass) {
        super(enumClass);
    }

    @Override
    public void serialize(final T value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        final String name = value != null ? value.getName() : null;
        gen.writeString(name);
    }
}
