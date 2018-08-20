package example.urlshortener.jc.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * This class is used to package up errors (trapoped by exceptions) when the API is called.
 */
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApiError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    ApiError(HttpStatus status, ApiException ex) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = ex.getMessage();
    }
}