package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Column
    @NotNull(message = "La horaComienzoReserva no puede ser nula, ingresa una hora con el patron HH:mm:ss")
    private Time horaComienzoReserva;

    @Column
    @NotNull(message = "La fechaInicialReserva no puede ser nula, ingresa una fecha con el patron yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_UY", timezone = "America/Montevideo")
    private java.sql.Date fechaInicialReserva;

    @Column
    @NotNull(message = "La fechaFinalReserva no puede ser nula, ingresa una fecha con el patron yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_UY", timezone = "America/Montevideo")
    private java.sql.Date fechaFinalReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "idProducto")
    private Producto producto;

    public Reserva() {
    }

    public Reserva(Time horaComienzoReserva, java.sql.Date fechaInicialReserva, java.sql.Date fechaFinalReserva, Usuario usuario, Producto producto) {
        this.horaComienzoReserva = horaComienzoReserva;
        this.fechaInicialReserva = fechaInicialReserva;
        this.fechaFinalReserva = fechaFinalReserva;
        this.usuario = usuario;
        this.producto = producto;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Time getHoraComienzoReserva() {
        return horaComienzoReserva;
    }

    public void setHoraComienzoReserva(Time horaComienzoReserva) {
        this.horaComienzoReserva = horaComienzoReserva;
    }

    public java.sql.Date getFechaInicialReserva() {
        return fechaInicialReserva;
    }

    public void setFechaInicialReserva(java.sql.Date fechaInicialReserva) {
        this.fechaInicialReserva = fechaInicialReserva;
    }

    public java.sql.Date getFechaFinalReserva() {
        return fechaFinalReserva;
    }

    public void setFechaFinalReserva(java.sql.Date fechaFinalReserva) {
        this.fechaFinalReserva = fechaFinalReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
