package by.mainservice.modules.user.core.entity.converter;

import by.mainservice.common.core.entity.converter.BaseEnumByIdConverter;
import by.mainservice.modules.user.core.entity.GenderType;

public class GenderTypeByIdConverter extends BaseEnumByIdConverter<GenderType> {

    public GenderTypeByIdConverter() {
        super(GenderType.class);
    }
}
