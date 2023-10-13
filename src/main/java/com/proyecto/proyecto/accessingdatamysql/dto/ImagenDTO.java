package com.proyecto.proyecto.accessingdatamysql.dto;

public class ImagenDTO {

    private Long id;

    private String titulo;

    private String image;

    private Long idProducto;

    public ImagenDTO() {
    }

    public ImagenDTO(String titulo, String image) {
        this.titulo = titulo;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return "ImagenDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", image='" + image + '\'' +
                ", idProducto=" + idProducto +
                '}';
    }
}
