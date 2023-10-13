package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Ciudad;
import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    @Query(value = "SELECT * FROM ciudad WHERE nombre = :name AND pais = :name2", nativeQuery = true)
    Optional<Ciudad> buscarCiudadNombrePais(@Param("name") String nombreCiudad, @Param("name2") String nombrePais);
}
