package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
//    @Query(value = "SELECT c FROM Caracteristica c WHERE nombre=?1")
//    Optional<Caracteristica> buscarCaracteristica(String nombre);

    @Query(value = "SELECT * FROM caracteristica WHERE nombre = ?1", nativeQuery = true)
    Optional<Caracteristica> buscarCaracteristicaNombre(String nombre);

    @Query(value = "SELECT * FROM caracteristica  WHERE nombre = :name", nativeQuery = true)
    Optional<Caracteristica> buscarCaracteristicaCompletaNombre(@Param("name") String nombre);
}
