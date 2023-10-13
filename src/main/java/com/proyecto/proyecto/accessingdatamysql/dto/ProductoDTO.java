package com.proyecto.proyecto.accessingdatamysql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.proyecto.accessingdatamysql.model.*;

import java.util.HashSet;
import java.util.Set;

public class ProductoDTO {
    private Long idProducto;

    private String titulo;

    private String descripcion;

    private Float precio;

    private Long valoracionDeSeguridad;

    private Long categoria;

    private Long ciudad;

    private Set<ImagenDTO> imagenes;

    private Set<Caracteristica> caracteristicas;

    private Set<Politica> politicas;

    public ProductoDTO() {
    }

    public ProductoDTO(Long idProducto, String titulo, String descripcion, Float precio, Long valoracionDeSeguridad, Long categoria, Long ciudad, Set<ImagenDTO> imagenes, Set<Caracteristica> caracteristicas) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.valoracionDeSeguridad = valoracionDeSeguridad;
        this.categoria = categoria;
        this.ciudad = ciudad;
        this.imagenes = imagenes;
        this.caracteristicas = caracteristicas;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "idProducto=" + idProducto +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria=" + categoria +
                ", ciudad=" + ciudad +
                ", imagenes=" + imagenes +
                ", caracteristicas=" + caracteristicas +
                '}';
    }

    public Long getValoracionDeSeguridad() {
        return valoracionDeSeguridad;
    }

    public void setValoracionDeSeguridad(Long valoracionDeSeguridad) {
        this.valoracionDeSeguridad = valoracionDeSeguridad;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Set<Politica> getPoliticas() {
        return politicas;
    }

    public void setPoliticas(Set<Politica> politicas) {
        this.politicas = politicas;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public Long getCiudad() {
        return ciudad;
    }

    public void setCiudad(Long ciudad) {
        this.ciudad = ciudad;
    }

    public Set<ImagenDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<ImagenDTO> imagenes) {
        this.imagenes = imagenes;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}

