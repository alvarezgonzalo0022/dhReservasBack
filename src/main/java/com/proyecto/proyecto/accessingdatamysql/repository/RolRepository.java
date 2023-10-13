package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import com.proyecto.proyecto.accessingdatamysql.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query(value = "SELECT * FROM rol WHERE nombre = :name", nativeQuery = true)
    Optional<Rol> buscarRolNombre(@Param("name") String nombreRol);
}
