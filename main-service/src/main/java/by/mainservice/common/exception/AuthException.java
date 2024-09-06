package by.mainservice.common.exception;

import lombok.Getter;

@Getter
public class AuthException extends ApplicationRuntimeException {

    private final Integer errorCode;

    public AuthException(final String message, final Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
