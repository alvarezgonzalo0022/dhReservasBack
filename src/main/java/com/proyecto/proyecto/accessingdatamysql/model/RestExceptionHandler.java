package com.proyecto.proyecto.accessingdatamysql.model;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.CustomConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    //other exception handlers

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExist(
            EntityExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.OK);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(
            BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Alguno de los datos requeridos se ingresó incorrectamente");

        Set<ConstraintViolation<?>> validationErrors = ex.getConstraintViolations();
        for (ConstraintViolation<?> validationError : validationErrors) {
            ApiValidationError apiValidationError = new ApiValidationError(validationError.getPropertyPath().toString(), validationError.getMessage());
            apiError.agregarSubError(apiValidationError);
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CustomConstraintViolationException.class)
    protected ResponseEntity<Object> handleCustomConstraintViolation(
            CustomConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Alguno de los datos requeridos se ingresó incorrectamente");

        Set<ApiValidationError> apiValidationErrors = ex.getValidationErrors();
        for (ApiValidationError apiValidationError : apiValidationErrors) {
            apiError.agregarSubError(apiValidationError);
        }

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
