package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(length = 90)
    @NotBlank(message = "El nombre del rol no puede ser nulo, ingresa un rol")
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Usuario> usuarios;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public void asignarRol() {
        for (Usuario usuario : usuarios) {
            usuario.setRol(this);
        }
    }

    public void agregarUsuarioEnRol(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
