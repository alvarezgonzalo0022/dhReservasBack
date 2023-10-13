package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.proyecto.accessingdatamysql.dto.ImagenDTO;
import com.proyecto.proyecto.accessingdatamysql.service.CaracteristicaService;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(length = 45)
    @NotBlank(message = "El titulo del producto no puede ser nulo, ingresa un titulo")
    private String titulo;

    @Column
    @NotBlank(message = "La descripcion del producto no puede ser nula, ingresa una descripcion")
    private String descripcion;

    @Column
    private Float precio;

    @Column
    private Long valoracionDeSeguridad;

    @ManyToOne()
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    private Categoria categoria;

    @ManyToOne()
    @JoinColumn(name = "idCiudad", referencedColumnName = "idCiudad")
    private Ciudad ciudad;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Imagen> imagenes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "productoxcaracteristica",
            joinColumns = {@JoinColumn(name = "idProducto")},
            inverseJoinColumns = {@JoinColumn(name = "idCaracteristica")}
    )
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "productoxpolitica",
            joinColumns = {@JoinColumn(name = "idProducto")},
            inverseJoinColumns = {@JoinColumn(name = "idPolitica")}
    )
    private Set<Politica> politicas = new HashSet<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();

    public void asignarProducto() {
        for (Imagen imagen:imagenes) {
            imagen.setProducto(this);
        }
    }

    public void agregarProductoEnCaracteristicas() {
        for (Caracteristica caracteristica:caracteristicas) {
            caracteristica.agregarProducto(this);
        }
    }

    public void agregarCaracteristicaEnProducto(Caracteristica caracteristica) {
        caracteristicas.add(caracteristica);
    }

    public void agregarPoliticaEnProducto(Politica politica) {
        politicas.add(politica);
    }

    public Producto() {
    }

    public Producto(String titulo, String descripcion, Float precio, Long valoracionDeSeguridad, Categoria categoria, Ciudad ciudad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.valoracionDeSeguridad = valoracionDeSeguridad;
        this.categoria = categoria;
        this.ciudad = ciudad;
    }

    public Long getValoracionDeSeguridad() {
        return valoracionDeSeguridad;
    }

    public void setValoracionDeSeguridad(Long valoracionDeSeguridad) {
        this.valoracionDeSeguridad = valoracionDeSeguridad;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Set<Politica> getPoliticas() {
        return politicas;
    }

    public void setPoliticas(Set<Politica> politicas) {
        this.politicas = politicas;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\'' +
                ", categoria=" + categoria +
                ", ciudad=" + ciudad +
                ", imagenes=" + imagenes +
                ", caracteristicas=" + caracteristicas +
                '}';
    }
}
