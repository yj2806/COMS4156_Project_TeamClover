package com.example.webservice.exception;

/**
 * Exception indicating that the provided client ID or authentication details are invalid.
 */
public class InvalidClientIDOrAuthException extends RuntimeException {

    /**
     * Constructs a new InvalidClientIDOrAuthException with the default message.
     */
    public InvalidClientIDOrAuthException() {
        super("Invalid client ID or authentication provided.");
    }

    /**
     * Constructs a new InvalidClientIDOrAuthException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidClientIDOrAuthException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidClientIDOrAuthException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public InvalidClientIDOrAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
