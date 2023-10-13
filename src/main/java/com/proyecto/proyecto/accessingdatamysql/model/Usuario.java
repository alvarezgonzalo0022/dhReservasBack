package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorColumn(name="usuario_tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(length = 45)
    @NotBlank(message = "El nombre del usuario no puede ser nulo, ingresa un nombre")
    private String nombre;

    @Column(length = 45)
    @NotBlank(message = "El apellido del usuario no puede ser nulo, ingresa un apellido")
    private String apellido;

    @Column
    @NotBlank(message = "El email no puede ser nulo, ingresa un email")
    private String email;

    @Column
    @NotBlank(message = "La contraseña no puede ser nula, ingresa una contraseña")
    @JsonIgnore
    private String contraseña;

    @Column
    private String ciudad;

//    @ManyToOne()
//    @JoinColumn(name = "idCiudad", referencedColumnName = "idCiudad")
//    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "idRol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String contraseña, String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
//        this.ciudad = ciudad;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
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

//    public Ciudad getCiudad() {
//        return ciudad;
//    }
//
//    public void setCiudad(Ciudad ciudad) {
//        this.ciudad = ciudad;
//    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
//                ", ciudad=" + ciudad +
                '}';
    }
}
