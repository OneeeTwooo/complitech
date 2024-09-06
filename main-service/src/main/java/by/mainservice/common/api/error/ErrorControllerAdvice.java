package by.mainservice.common.api.error;

import by.mainservice.common.api.error.dto.ErrorResponseDto;
import by.mainservice.common.api.error.dto.ErrorType;
import by.mainservice.common.enums.EnumConverterException;
import by.mainservice.common.exception.ApplicationRuntimeException;
import by.mainservice.common.exception.AuthException;
import by.mainservice.common.exception.PasswordValidationException;
import by.mainservice.common.exception.ValueNotFoundException;
import by.mainservice.common.uuid.UuidGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ErrorControllerAdvice {

    private static final String DEFAULT_ERROR_MESSAGE_PATTERN = "ErrorId [%s], message [%s], httpStatus [%s]";
    private static final String DEFAULT_INTERNAL_SERVER_MESSAGE = "Ошибка сервера";

    public static final Integer DEFAULT_SERVER_CODE = 1;

    private final UuidGenerator uuidGenerator;

    @ExceptionHandler(value = ValueNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNotFoundException(final ValueNotFoundException exception) {
        final var errorId = uuidGenerator.generateAsString();
        final var errorMessage = getDefaultErrorMessage(errorId, exception.getMessage(), HttpStatus.BAD_REQUEST);

        log.error(errorMessage);

        return ErrorResponseDto.builder()
                .errorType(ErrorType.PROCESSING)
                .errorCode(exception.getErrorCode())
                .errorStatus(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .errorId(errorId)
                .build();
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponseDto handleAccessDeniedException(final AccessDeniedException exception) {
//        final var errorId = uuidGenerator.generateAsString();
//        final var errorMessage = getDefaultErrorMessage(errorId, exception.getMessage(), HttpStatus.FORBIDDEN);
//
//        log.error(errorMessage);
//
//        return ErrorResponseDto.builder()
//                .errorType(ErrorType.SECURITY)
//                .errorCode(ACCESS_DENIED_CODE)
//                .errorStatus(HttpStatus.FORBIDDEN.value())
//                .message("Доступ запрещен")
//                .errorId(errorId)
//                .build();
//    }

//    @ExceptionHandler(value = LoginActiveTokenException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponseDto handleLoginActiveTokenException(final LoginActiveTokenException ex) {
//        final var errorId = uuidGenerator.generateAsString();
//        final var errorMessage = getDefaultErrorMessage(errorId, ex.getMessage(), HttpStatus.FORBIDDEN);
//
//        log.error(errorMessage);
//
//        return ErrorResponseDto.builder()
//                .errorType(ErrorType.SECURITY)
//                .errorCode(ex.getErrorCode())
//                .errorStatus(HttpStatus.FORBIDDEN.value())
//                .message(ex.getMessage())
//                .errorId(errorId)
//                .build();
//    }

    @ExceptionHandler(
            value = {
                    EnumConverterException.class,
                    ApplicationRuntimeException.class,
                    PasswordValidationException.class,
                    AuthException.class,
                    Exception.class
            })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleServerException(final Exception exception) {
        final var errorId = uuidGenerator.generateAsString();
        final var errorMessage =
                getDefaultErrorMessage(errorId, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error(errorMessage, exception);

        Integer errorCode;
        String message;

        switch (exception) {
            case final EnumConverterException enumConverterException -> {
                errorCode = DEFAULT_SERVER_CODE;
                message = enumConverterException.getMessage();
            }
            case final HttpMessageNotReadableException httpMessageNotReadableException -> {
                errorCode = DEFAULT_SERVER_CODE;
                message = httpMessageNotReadableException.getMessage();
            }
            case final PasswordValidationException passwordValidationException -> {
                errorCode = passwordValidationException.getErrorCode();
                message = passwordValidationException.getMessage();
            }
            case final AuthException authException -> {
                errorCode = authException.getErrorCode();
                message = authException.getMessage();
            }
            default -> {
                errorCode = DEFAULT_SERVER_CODE;
                message = DEFAULT_INTERNAL_SERVER_MESSAGE;
            }
        }

        return ErrorResponseDto.builder()
                .errorType(ErrorType.SYSTEM)
                .errorCode(errorCode)
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .errorId(errorId)
                .build();
    }

    private static String getDefaultErrorMessage(
            final String errorId, final String message, final HttpStatus httpStatus) {
        return DEFAULT_ERROR_MESSAGE_PATTERN.formatted(errorId, message, httpStatus.name());
    }
}
