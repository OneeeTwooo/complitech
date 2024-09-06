package by.mainservice.modules.user.api.dto.deserializer;

import by.mainservice.common.enums.EnumConverterException;
import by.mainservice.common.enums.deserializer.BaseEnumByIdDeserializer;
import by.mainservice.modules.user.core.entity.GenderType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class GenderTypeByIdDeserializer extends BaseEnumByIdDeserializer<GenderType> {

    public GenderTypeByIdDeserializer() {
        super(GenderType.class);
    }

    @Override
    public GenderType deserialize(final JsonParser parser, final DeserializationContext context)
            throws IOException {
        final int id = parser.getValueAsInt();

        if (id < 1 || id > 3) {
            throw new EnumConverterException("Гендер должен быть между 1 и 3");
        }

        return super.deserialize(parser, context);
    }
}
