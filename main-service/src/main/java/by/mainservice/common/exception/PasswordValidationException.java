package by.mainservice.common.exception;

import lombok.Getter;

@Getter
public class PasswordValidationException extends ApplicationRuntimeException {

    private final Integer errorCode;

    public PasswordValidationException(final String message, final Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
