package com.vginert.brastlewark.data.exception;

import com.vginert.brastlewark.domain.exception.IErrorBundle;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 *
 * @author Vicente Giner Tendero
 */
class RepositoryErrorBundle implements IErrorBundle {

    private final Exception exception;

    RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            message = this.exception.getMessage();
        }
        return message;
    }
}
