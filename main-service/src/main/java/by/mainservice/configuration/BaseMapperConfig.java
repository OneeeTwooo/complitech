package by.mainservice.configuration;

import lombok.experimental.UtilityClass;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
@UtilityClass
public class BaseMapperConfig {}
