package com.vginert.brastlewark.domain.exception;

/**
 * Wrapper around Exceptions used to manage default errors.
 *
 * @author Vicente Giner Tendero
 */
public class DefaultErrorBundle implements IErrorBundle {

    private static final String DEFAULT_ERROR_MSG = "Unknown error";

    private final Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? this.exception.getMessage() : DEFAULT_ERROR_MSG;
    }
}
