package com.proyecto.proyecto.accessingdatamysql.dto;

import java.util.Set;

public class RolDTO {

    private Long idRol;

    private String nombre;

    private Set<UsuarioDTO> usuarios;

    public RolDTO(String nombre) {
        this.nombre = nombre;
    }

    public RolDTO() {
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

    public Set<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}
