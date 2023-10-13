package com.proyecto.proyecto.accessingdatamysql.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
//@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
    private String nombreDelDatoRequerido;
//    private String field;
//    private Object rejectedValue;
    private String message;

    public ApiValidationError(String nombreDelDatoRequerido, String message) {
        this.nombreDelDatoRequerido = nombreDelDatoRequerido;
        this.message = message;
    }
}
