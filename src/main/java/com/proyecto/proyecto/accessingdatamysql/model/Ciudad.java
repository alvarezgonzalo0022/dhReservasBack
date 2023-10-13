package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ciudad")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCiudad;

    @Column(length = 45)
    @NotBlank(message = "El nombre de la ciudad no puede ser nulo, ingresa un nombre")
    private String nombre;

    @Column(length = 45)
    @NotBlank(message = "El pais no puede ser nulo, ingresa un pais")
    private String pais;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

//    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Producto> usuarios = new HashSet<>();

    public Ciudad() {
    }

    public Ciudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

//    public Set<Producto> getUsuarios() {
//        return usuarios;
//    }

//    public void setUsuarios(Set<Producto> usuarios) {
//        this.usuarios = usuarios;
//    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
