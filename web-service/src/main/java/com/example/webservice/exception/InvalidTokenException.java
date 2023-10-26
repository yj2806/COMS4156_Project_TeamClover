package com.example.webservice.exception;

/**
 * Exception indicating that the provided token is invalid.
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Constructs a new InvalidTokenException with the default message.
     */
    public InvalidTokenException() {
        super("Invalid token provided.");
    }

    /**
     * Constructs a new InvalidTokenException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidTokenException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidTokenException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
