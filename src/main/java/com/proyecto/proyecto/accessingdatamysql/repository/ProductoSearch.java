package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.model.Producto;

import java.util.List;

public interface ProductoSearch {
    List<Producto> filtrarPorCiudadCategoriaTitulo(String titulo, String categoria, String ciudad) throws BadRequestException;
}
