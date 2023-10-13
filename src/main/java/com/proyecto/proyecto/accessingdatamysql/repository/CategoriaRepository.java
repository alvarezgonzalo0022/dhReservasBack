package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "SELECT c FROM Categoria c WHERE titulo=?1")
    Optional<Categoria> buscarCategoria(String titulo);

    @Query(value = "SELECT * FROM categoria WHERE titulo = :name", nativeQuery = true)
    Optional<Categoria> buscarCategoriaTitulo(@Param("name") String tituloCategoria);
}
