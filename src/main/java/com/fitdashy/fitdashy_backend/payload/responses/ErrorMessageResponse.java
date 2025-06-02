package com.fitdashy.fitdashy_backend.payload.responses;

public class ErrorMessageResponse {
    private String message;
    private String exception;

    public ErrorMessageResponse(String message, Exception exception) {
        this.message = message;
        this.setException(exception);
    }

    public ErrorMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception.getMessage();
    }
}