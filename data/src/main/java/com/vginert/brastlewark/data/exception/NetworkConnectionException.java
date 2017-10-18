package com.vginert.brastlewark.data.exception;

/**
 * Exception throw by the application when a there is a network connection exception.
 *
 * @author Vicente Giner Tendero
 */
public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
        super();
    }

    public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }
}
