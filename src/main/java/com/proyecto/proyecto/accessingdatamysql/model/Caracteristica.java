package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "caracteristica")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaracteristica;

    @Column(length = 45)
    @NotBlank(message = "El nombre de la caracteristica no puede ser nulo, ingresa un nombre correcto")
    private String nombre;

    @Column(length = 45)
//    @NotBlank(message = "El icono de la caracteristica no puede ser nulo, ingresa una url de un icono")
    private String icono;

    @ToStringPlugin.Exclude
    @ManyToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    public Caracteristica() {
    }

    public Caracteristica(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }


    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Long getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Long idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
                "idCaracteristica=" + idCaracteristica +
                ", nombre='" + nombre;
    }
}
