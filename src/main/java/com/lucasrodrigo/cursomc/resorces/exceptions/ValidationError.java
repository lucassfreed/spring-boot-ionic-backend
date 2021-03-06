package com.lucasrodrigo.cursomc.resorces.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    public static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addEror(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
