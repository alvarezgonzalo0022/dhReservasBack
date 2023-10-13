package com.proyecto.proyecto.accessingdatamysql.repository;

import com.proyecto.proyecto.accessingdatamysql.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long>, ProductoSearch {

    @Query(value = "SELECT * FROM producto ORDER BY RAND() LIMIT 8", nativeQuery = true)
    List<Producto> getRandom8();

    @Query(value = "SELECT id_caracteristica FROM caracteristica c WHERE c.nombre = :name", nativeQuery = true)
    Optional<Integer> buscarCaracteristicaNombre(@Param("name") String nombre);

    @Query(value = "SELECT producto.* FROM producto INNER JOIN ciudad ON producto.id_ciudad = ciudad.id_ciudad WHERE ciudad.nombre = :name", nativeQuery = true)
    List<Producto> filtrarProductosPorCiudad(@Param("name") String nombreCiudad);

    @Query(value = "SELECT producto.* FROM producto INNER JOIN categoria ON producto.id_categoria = categoria.id_categoria WHERE categoria.titulo = :name", nativeQuery = true)
    List<Producto> filtrarProductosPorCategoria(@Param("name") String tituloCategoria);

    @Query(value = "SELECT * FROM producto WHERE NOT EXISTS (SELECT * FROM reserva WHERE (reserva.id_producto = producto.id_producto) AND (:fechaInicial <= reserva.fecha_final_reserva) AND (:fechaFinal >= reserva.fecha_inicial_reserva));", nativeQuery = true)
    List<Producto> filtrarProductosPorRangoDeFechas(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

    @Query(value = "SELECT producto.* FROM producto INNER JOIN ciudad ON producto.id_ciudad = ciudad.id_ciudad WHERE (ciudad.nombre = :nombreCiudad) AND NOT EXISTS (SELECT * FROM reserva WHERE (reserva.id_producto = producto.id_producto) AND (:fechaInicial <= reserva.fecha_final_reserva) AND (:fechaFinal >= reserva.fecha_inicial_reserva));", nativeQuery = true)
    List<Producto> filtrarProductosPorCiudadYRangoDeFechas(@Param("nombreCiudad") String nombreCiudad, @Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

}
