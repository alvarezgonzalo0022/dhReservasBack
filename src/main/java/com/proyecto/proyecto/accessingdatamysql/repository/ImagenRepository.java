package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    @Query(value = "SELECT * FROM imagen WHERE image = :name", nativeQuery = true)
    Optional<Imagen> buscarImagenUrl(@Param("name") String urlImagen);
}
