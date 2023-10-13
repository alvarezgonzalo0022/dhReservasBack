package com.proyecto.proyecto.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "imagen")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    @NotBlank(message = "El titulo no puede ser nulo, ingresa un titulo")
    private String titulo;

    @Column
    @NotBlank(message = "La url de imagen no puede ser nula, ingresa una url")
    private String image;

    @ToStringPlugin.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", nullable = false)
    private Producto producto;


    public Imagen() {
    }

    public Imagen(String titulo, String image) {
        this.titulo = titulo;
        this.image = image;
    }

    public Imagen(Producto producto, String titulo, String image) {
        this.producto = producto;
        this.titulo = titulo;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
