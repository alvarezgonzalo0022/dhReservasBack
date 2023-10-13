package com.proyecto.proyecto.accessingdatamysql.model;

import com.proyecto.proyecto.accessingdatamysql.dto.ImagenDTO;
import com.proyecto.proyecto.accessingdatamysql.dto.ProductoDTO;
import com.proyecto.proyecto.accessingdatamysql.dto.ReservaDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.CustomConstraintViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class Validator {

    public Validator() {
    }

    public ImagenDTO validarImagenDTO(ImagenDTO imagenDTO) {
        Set<ApiValidationError> validationErrors = new HashSet<>();

        //Validar idProducto

        if (imagenDTO.getIdProducto() == null || Objects.equals(String.valueOf(imagenDTO.getIdProducto()), "")) {
            validationErrors.add(new ApiValidationError("idProducto", "El número de ID del producto no puede ser nulo"));
        }
        else {
            if (imagenDTO.getIdProducto() <= 0) {
                validationErrors.add(new ApiValidationError("idProducto", "El número de ID del producto debe ser un numero positivo"));
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new CustomConstraintViolationException("", validationErrors);
        }

        return imagenDTO;
    }

    public ProductoDTO validarProductoDTO(ProductoDTO productoDTO) {
        Set<ApiValidationError> validationErrors = new HashSet<>();

        //Validar precio

        if (productoDTO.getPrecio() == null || Objects.equals(String.valueOf(productoDTO.getPrecio()), "")) {
            validationErrors.add(new ApiValidationError("precio", "El precio no puede ser nulo"));
        }
        else {
            if (productoDTO.getPrecio() < 0) {
                validationErrors.add(new ApiValidationError("precio", "El precio debe ser 0 o un número positivo"));
            }
        }

        //Validar valoracionDeSeguridad

        if (productoDTO.getValoracionDeSeguridad() == null || Objects.equals(String.valueOf(productoDTO.getValoracionDeSeguridad()), "")) {
            validationErrors.add(new ApiValidationError("valoracionDeSeguridad", "La valoracion de seguridad no puede ser nula"));
        }
        else {
            if (productoDTO.getValoracionDeSeguridad() < 0 || productoDTO.getValoracionDeSeguridad() > 5) {
                validationErrors.add(new ApiValidationError("valoracionDeSeguridad", "La valoracion de seguridad debe ser un numero del 0 al 5, inclusive"));
            }
        }

        //Validar idCategoria

        if (productoDTO.getCategoria() == null || Objects.equals(String.valueOf(productoDTO.getCategoria()), "")) {
            validationErrors.add(new ApiValidationError("categoria", "El número de ID de la categoria no puede ser nulo"));
        }
        else {
            if (productoDTO.getCategoria() <= 0) {
                validationErrors.add(new ApiValidationError("categoria", "El número de ID de la categoria debe ser un numero positivo"));
            }
        }

        //Validar idCiudad

        if (productoDTO.getCiudad() == null || Objects.equals(String.valueOf(productoDTO.getCiudad()), "")) {
            validationErrors.add(new ApiValidationError("ciudad", "El número de ID de la ciudad no puede ser nulo"));
        }
        else {
            if (productoDTO.getCiudad() <= 0) {
                validationErrors.add(new ApiValidationError("ciudad", "El número de ID de la ciudad debe ser un numero positivo"));
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new CustomConstraintViolationException("", validationErrors);
        }

        return productoDTO;
    }

    public ReservaDTO validarReservaDTO(ReservaDTO reservaDTO) {
        Set<ApiValidationError> validationErrors = new HashSet<>();

        //Validar idUsuario

        if (reservaDTO.getCliente() == null || Objects.equals(String.valueOf(reservaDTO.getCliente()), "")) {
            validationErrors.add(new ApiValidationError("cliente", "El número de ID del cliente no puede ser nulo"));
        }
        else {
            if (reservaDTO.getCliente() <= 0) {
                validationErrors.add(new ApiValidationError("cliente", "El número de ID del cliente debe ser un numero positivo"));
            }
        }

        //Validar idProducto

        if (reservaDTO.getProducto() == null || Objects.equals(String.valueOf(reservaDTO.getProducto()), "")) {
            validationErrors.add(new ApiValidationError("producto", "El número de ID del producto no puede ser nulo"));
        }
        else {
            if (reservaDTO.getProducto() <= 0) {
                validationErrors.add(new ApiValidationError("producto", "El número de ID del producto debe ser un numero positivo"));
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new CustomConstraintViolationException("", validationErrors);
        }

        return reservaDTO;
    }

}
