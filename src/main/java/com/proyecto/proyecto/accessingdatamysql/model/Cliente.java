package com.proyecto.proyecto.accessingdatamysql.model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value="cliente")
public class Cliente extends Usuario{

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String email, String contraseña, String ciudad) {
        super(nombre, apellido, email, contraseña, ciudad);
    }

}
