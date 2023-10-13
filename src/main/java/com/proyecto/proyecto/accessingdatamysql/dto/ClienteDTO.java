package com.proyecto.proyecto.accessingdatamysql.dto;

public class ClienteDTO extends UsuarioDTO {

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre, String apellido, String email, String contraseña, Long idRol, String ciduad) {
        super(nombre, apellido, email, contraseña, idRol, ciduad);
    }
}
