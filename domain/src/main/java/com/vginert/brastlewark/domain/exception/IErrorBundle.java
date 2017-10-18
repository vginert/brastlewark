package com.vginert.brastlewark.domain.exception;

/**
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 *
 * @author Vicente Giner Tendero
 */
public interface IErrorBundle {

    Exception getException();

    String getErrorMessage();
}
