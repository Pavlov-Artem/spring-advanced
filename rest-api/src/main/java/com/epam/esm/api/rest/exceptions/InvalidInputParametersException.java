package com.epam.esm.api.rest.exceptions;

public class InvalidInputParametersException extends RuntimeException {

    public InvalidInputParametersException() {
        super();
    }

    public InvalidInputParametersException(String message) {
        super(message);
    }

    public InvalidInputParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputParametersException(Throwable cause) {
        super(cause);
    }

    protected InvalidInputParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
