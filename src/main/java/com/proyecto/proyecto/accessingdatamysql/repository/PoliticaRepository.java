package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.model.Politica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PoliticaRepository extends JpaRepository<Politica, Long> {

    @Query(value = "SELECT * FROM politica WHERE nombre = :name", nativeQuery = true)
    Optional<Politica> buscarPoliticaCompletaNombre(@Param("name") String nombre);
}
