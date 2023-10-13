package com.proyecto.proyecto.accessingdatamysql.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

public class ReservaDTO {

    private Long idReserva;

    @NotNull
    private Time horaComienzoReserva;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_UY", timezone = "America/Montevideo")
    @NotNull
    private java.sql.Date fechaInicialReserva;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_UY", timezone = "America/Montevideo")
    @NotNull
    private java.sql.Date fechaFinalReserva;

    @NotNull
    private Long cliente;

    @NotNull
    private Long producto;

    public ReservaDTO() {
    }

    public ReservaDTO(Time horaComienzoReserva, java.sql.Date fechaInicialReserva, java.sql.Date fechaFinalReserva, Long cliente, Long producto) {
        this.horaComienzoReserva = horaComienzoReserva;
        this.fechaInicialReserva = fechaInicialReserva;
        this.fechaFinalReserva = fechaFinalReserva;
        this.cliente = cliente;
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

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }
}
