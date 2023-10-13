package com.proyecto.proyecto.accessingdatamysql.exceptions;

import com.proyecto.proyecto.accessingdatamysql.model.ApiValidationError;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomConstraintViolationException extends ValidationException {
    private String message;
    private Set<ApiValidationError> validationErrors = new HashSet<>();

    public CustomConstraintViolationException(String message, Set<ApiValidationError> validationErrors) {
        this.message = message;
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<ApiValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Set<ApiValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
