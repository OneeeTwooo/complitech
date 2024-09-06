package by.mainservice.common.exception;

import lombok.Getter;

@Getter
public class ValueNotFoundException extends ApplicationRuntimeException {

    private final Integer errorCode;

    public ValueNotFoundException(final String message, final Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
