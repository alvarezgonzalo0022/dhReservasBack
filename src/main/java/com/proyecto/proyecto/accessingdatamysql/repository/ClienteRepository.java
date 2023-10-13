package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM usuario WHERE email = :name", nativeQuery = true)
    Optional<Cliente> buscarClienteEmail(@Param("name") String emailCliente);
}
