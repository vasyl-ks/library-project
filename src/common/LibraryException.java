package common;

/**
 * Custom exception for the library system.
 * Used to indicate errors or invalid operations
 * related to library management.
 */
public class LibraryException extends Exception {

    /**
     * Constructs a new LibraryException with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public LibraryException(String message) {
        super(message);
    }
}
