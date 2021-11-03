package org.ummjevel.osmapi;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String result;
    private String message;

    public RestException(HttpStatus status, String result, String message) {
        this.status = status;
        this.result = result;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
