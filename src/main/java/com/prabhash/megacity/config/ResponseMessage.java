package com.prabhash.megacity.config;

import java.util.List;

public class ResponseMessage {
    private String message;
    private List<String> errors;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
