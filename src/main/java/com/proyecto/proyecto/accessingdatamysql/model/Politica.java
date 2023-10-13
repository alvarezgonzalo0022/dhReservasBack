package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "politica")
public class Politica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPolitica;

    @Column(length = 90)
    @NotBlank(message = "El nombre de la politica no puede ser nulo, ingresa un nombre")
    private String nombre;

    @Column
    @NotBlank(message = "La descripcion de la politica no puede ser nula, ingresa una descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "politicas")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    public Politica() {
    }

    public Politica(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getIdPolitica() {
        return idPolitica;
    }

    public void setIdPolitica(Long idPolitica) {
        this.idPolitica = idPolitica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Politica{" +
                "idPolitica=" + idPolitica +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
