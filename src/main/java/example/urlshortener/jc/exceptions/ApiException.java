package example.urlshortener.jc.exceptions;

/**
 * Exception for application level errors etc.
 */
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }
}
