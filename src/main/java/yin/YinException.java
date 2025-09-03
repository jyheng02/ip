package yin;
/**
 * Custom exception used.
 * Thrown when the user gives bad input or when something goes wrong.
 */
public class YinException extends Exception {
    /**
     * Makes a new YinException with a message to explain what went wrong.
     *
     * @param message the detail message
     */
    public YinException(String message) {
        super(message);
    }
}
