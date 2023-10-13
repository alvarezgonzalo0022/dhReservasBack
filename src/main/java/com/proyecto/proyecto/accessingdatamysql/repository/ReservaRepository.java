package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import com.proyecto.proyecto.accessingdatamysql.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    //Buscar una reserva con el mismo producto, usuario, fecha inicial y fecha final
    @Query(value = "SELECT * FROM reserva WHERE (id_producto = :producto) AND (id_Usuario = :cliente) AND (fecha_inicial_reserva  = :fechaInicial) AND (fecha_final_reserva = :fechaFinal)", nativeQuery = true)
    Optional<Reserva> buscarReservaProductoClienteFechas(@Param("producto") Long idProducto, @Param("cliente") Long idCliente, @Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);


    //Buscar lista de reservas que contengan parcial o completamente las fechas ingresadas
    @Query(value = "SELECT * FROM reserva WHERE (:fechaInicial <= fecha_final_reserva) AND (:fechaFinal >= fecha_inicial_reserva)", nativeQuery = true)
    List<Reserva> buscarReservasPorRangoDeFechas(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

    @Query(value = "SELECT reserva.* FROM reserva INNER JOIN producto ON reserva.id_producto = producto.id_producto WHERE producto.id_producto = :idProducto", nativeQuery = true)
    List<Reserva> buscarReservasPorIdProducto(@Param("idProducto") Long idProducto);

    @Query(value = "SELECT reserva.* FROM reserva INNER JOIN usuario ON reserva.id_usuario = usuario.id_usuario WHERE usuario.id_usuario = :idUsuario", nativeQuery = true)
    List<Reserva> buscarReservasPorIdUsuario(@Param("idUsuario") Long idUsuario);
}
