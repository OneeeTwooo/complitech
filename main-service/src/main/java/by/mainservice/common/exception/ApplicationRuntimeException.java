package by.mainservice.common.exception;

import lombok.Getter;

@Getter
public class ApplicationRuntimeException extends RuntimeException {

    public ApplicationRuntimeException(final String message) {
        super(message);
    }

    public ApplicationRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
