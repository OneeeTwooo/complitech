package by.mainservice.common.api.error.dto;


import by.mainservice.common.api.error.dto.serializer.ErrorTypeByNameSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ErrorResponseDto {

    @JsonSerialize(using = ErrorTypeByNameSerializer.class)
    private final ErrorType errorType;

    private final Integer errorCode;

    private final Integer errorStatus;

    private final String message;

    private final String errorId;
}
