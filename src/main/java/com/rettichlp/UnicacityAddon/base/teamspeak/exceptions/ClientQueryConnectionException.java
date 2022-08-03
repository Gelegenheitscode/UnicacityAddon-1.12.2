package com.rettichlp.UnicacityAddon.base.teamspeak.exceptions;

/**
 * @author Fuzzlemann
 */
public class ClientQueryConnectionException extends ClientQueryException {
    public ClientQueryConnectionException() {
        super();
    }

    public ClientQueryConnectionException(String message) {
        super(message);
    }

    public ClientQueryConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientQueryConnectionException(Throwable cause) {
        super(cause);
    }
}
