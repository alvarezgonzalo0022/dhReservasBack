package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.model.Ciudad;
import com.proyecto.proyecto.accessingdatamysql.model.Politica;
import com.proyecto.proyecto.accessingdatamysql.model.Producto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoSearchImpl implements ProductoSearch{

    private final EntityManager em;

    public ProductoSearchImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Producto> filtrarPorCiudadCategoriaTitulo(String titulo, String categoria, String ciudad) throws BadRequestException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);

        //SELECT * FROM producto

        Root<Producto> root = criteriaQuery.from(Producto.class);

        //INNER JOIN ciudad

        Join<Producto, Ciudad> ciudadJoin = root.join("ciudad", JoinType.INNER);

        //INNER JOIN categoria

        Join<Producto, Categoria> categoriaJoin = root.join("categoria", JoinType.INNER);

        //WHERE

        List<Predicate> predicates = new ArrayList<>();
        Boolean algunAtributoEsValido = false;


        if (categoria != null && !categoria.equals("null") && !categoria.equals("")) {
            Predicate categoriaPredicate = criteriaBuilder.equal(categoriaJoin.get("titulo"), categoria);
            predicates.add(categoriaPredicate);
            algunAtributoEsValido = true;
        }

        if (ciudad != null && !ciudad.equals("null") && !ciudad.equals("")) {
            Predicate ciudadPredicate =  criteriaBuilder.equal(ciudadJoin.get("nombre"), ciudad);
            predicates.add(ciudadPredicate);
            algunAtributoEsValido = true;
        }

        if (titulo != null && !titulo.equals("null") && !titulo.equals("")) {
            Predicate tituloPredicate = criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%");
            predicates.add(tituloPredicate);
            algunAtributoEsValido = true;
        }

        if (!algunAtributoEsValido) {
            throw new BadRequestException("No has enviado ninguno de los filtros posibles mediante la url");
        }

        Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(andPredicate);
        TypedQuery<Producto> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }


    public List<Producto> filtrarProductosPaginados(String start, int limit, String titulo, String categoria, String ciudad) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);

        //SELECT * FROM producto

        Root<Producto> root = criteriaQuery.from(Producto.class);

        //INNER JOIN ciudad ON producto.id_ciudad = ciudad.id_ciudad

        Join<Producto, Ciudad> ciudadJoin = root.join("ciudad", JoinType.INNER);

        //INNER JOIN categoria ON producto.id_categoria = ciudad.id_categoria

        Join<Producto, Categoria> categoriaJoin = root.join("categoria", JoinType.INNER);

        //WHERE

        List<Predicate> predicates = new ArrayList<>();

        //:start > producto.id_producto

        if (start != null && !start.equals("null") && !start.equals("")) {
            Predicate startPredicate = criteriaBuilder.greaterThan(root.get("idProducto"), start);
            predicates.add(startPredicate);
        }

        //producto.titulo = :titulo

        if (titulo != null && !titulo.equals("null") && !titulo.equals("")) {
            Predicate tituloPredicate = criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%");
            predicates.add(tituloPredicate);
        }

        //categoria.titulo = :categoria

        if (categoria != null && !categoria.equals("null") && !categoria.equals("")) {
            Predicate categoriaPredicate = criteriaBuilder.equal(categoriaJoin.get("titulo"), categoria);
            predicates.add(categoriaPredicate);
        }

        //ciudad.nombre = :ciudad

        if (ciudad != null && !ciudad.equals("null") && !ciudad.equals("")) {
            Predicate ciudadPredicate =  criteriaBuilder.equal(ciudadJoin.get("nombre"), ciudad);
            predicates.add(ciudadPredicate);
        }

        //AND

        Predicate andPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(andPredicate);

        //ORDER BY producto.id_producto ASC

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("idProducto")));

        //Limit = :limit

        TypedQuery<Producto> query = em.createQuery(criteriaQuery).setMaxResults(limit);

        return query.getResultList();
    }


    public List<Producto> listarProductosPaginados(String start, int limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);

        Root<Producto> root = criteriaQuery.from(Producto.class);

        List<Predicate> predicates = new ArrayList<>();

        if (start != null && !start.equals("null") && !start.equals("")) {
            Predicate startPredicate = criteriaBuilder.greaterThan(root.get("idProducto"), start);
            predicates.add(startPredicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("idProducto")));

        TypedQuery<Producto> query = em.createQuery(criteriaQuery).setMaxResults(limit);

        return query.getResultList();
    }
}
