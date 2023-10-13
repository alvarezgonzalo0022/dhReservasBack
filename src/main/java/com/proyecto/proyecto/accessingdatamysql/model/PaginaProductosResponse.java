package com.proyecto.proyecto.accessingdatamysql.model;

import java.util.List;

public class PaginaProductosResponse {

    private Next next;

    private Boolean hasMore;

    private List<Producto> productos;

    public PaginaProductosResponse(Next next, Boolean hasMore, List<Producto> productos) {
        this.next = next;
        this.productos = productos;
        this.hasMore = hasMore;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
