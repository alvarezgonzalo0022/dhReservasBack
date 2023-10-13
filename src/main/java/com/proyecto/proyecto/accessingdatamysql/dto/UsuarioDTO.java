package com.proyecto.proyecto.accessingdatamysql.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.proyecto.accessingdatamysql.model.Reserva;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UsuarioDTO {

    private Long idUsuario;


    private String nombre;


    private String apellido;


    private String email;


    private String contraseña;

//    private Long idCiudad;

    private String ciudad;

    @JsonIgnore
    private Long idRol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombre, String apellido, String email, String contraseña, Long idRol, String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
//        this.idCiudad = idCiudad;
        this.idRol = idRol;
        this.ciudad = ciudad;
    }


    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

//    public Long getIdCiudad() {
//        return idCiudad;
//    }
//
//    public void setIdCiudad(Long idCiudad) {
//        this.idCiudad = idCiudad;
//    }


    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
//                ", idCiudad=" + idCiudad +
                ", idRol=" + idRol +
                '}';
    }
}
