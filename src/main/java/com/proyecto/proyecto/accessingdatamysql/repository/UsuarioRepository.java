package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import com.proyecto.proyecto.accessingdatamysql.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario WHERE email = :name", nativeQuery = true)
    Optional<Usuario> buscarUsuarioEmail(@Param("name") String emailUsuario);
}
