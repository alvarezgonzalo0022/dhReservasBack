package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.dto.ProductoDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.model.PaginaProductosResponse;
import com.proyecto.proyecto.accessingdatamysql.model.Producto;
import com.proyecto.proyecto.accessingdatamysql.service.CaracteristicaService;
import com.proyecto.proyecto.accessingdatamysql.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productos")
public class ProductoController {
    private ProductoService productoService;
    private CaracteristicaService caracteristicaService;

    @Autowired
    public ProductoController(ProductoService productoService, CaracteristicaService caracteristicaService) {
        this.productoService = productoService;
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> buscarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/paginados")
    public ResponseEntity<PaginaProductosResponse> buscarProductosPaginados(@RequestParam(value = "start", required = false) String start) {
        PaginaProductosResponse paginaProductos = productoService.listarProductosPaginados(start);
        return ResponseEntity.ok(paginaProductos);
    }

    @GetMapping("/filtrarProductosPaginados")
    public ResponseEntity<PaginaProductosResponse> filtrarProductosPaginados(@RequestParam(value = "start", required = false) String start, @RequestParam(value = "titulo", required = false) String titulo,@RequestParam(value = "categoria", required = false) String categoria, @RequestParam(value = "ciudad", required = false) String ciudad) {
        PaginaProductosResponse paginaProductos = productoService.filtrarProductosPaginados(start, titulo, categoria, ciudad);
        return ResponseEntity.ok(paginaProductos);
    }

    @GetMapping("filtrarPorCiudad/{nombreCiudad}")
    public ResponseEntity<List<Producto>> buscarProductosPorCiudad(@PathVariable String nombreCiudad) {
        return ResponseEntity.ok(productoService.listarProductosPorCiudad(nombreCiudad));
    }

    @GetMapping("filtrarPorRangoDeFechas/{fechaInicial}/{fechaFinal}")
    public ResponseEntity<List<Producto>> buscarProductosPorRangoDeFechas(@PathVariable String fechaInicial, @PathVariable String fechaFinal) throws ParseException {
        return ResponseEntity.ok(productoService.listarProductosPorRangoDeFechas(fechaInicial, fechaFinal));
    }

    @GetMapping("filtrarPorCiudadYRangoDeFechas/{nombreCiudad}/{fechaInicial}/{fechaFinal}")
    public ResponseEntity<List<Producto>> buscarProductosPorCiudadYRangoDeFechas(@PathVariable String nombreCiudad, @PathVariable String fechaInicial, @PathVariable String fechaFinal) throws ParseException {
        return ResponseEntity.ok(productoService.listarProductosPorCiudadYRangoDeFechas(nombreCiudad, fechaInicial, fechaFinal));
    }

    @GetMapping("filtrarPorCategoria/{tituloCategoria}")
    public ResponseEntity<List<Producto>> buscarProductosPorCategoria(@PathVariable String tituloCategoria) {
        return ResponseEntity.ok(productoService.listarProductosPorCategoria(tituloCategoria));
    }

    @GetMapping("search")
    public ResponseEntity<List<Producto>> search(@RequestParam(value = "titulo", required = false) String titulo,@RequestParam(value = "categoria", required = false) String categoria, @RequestParam(value = "ciudad", required = false) String ciudad) throws BadRequestException {
        System.out.println(titulo);
        System.out.println(categoria);
        System.out.println(ciudad);
        return ResponseEntity.ok(productoService.buscar(titulo, categoria, ciudad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable Long id) {
        Optional<Producto> productoBuscado = productoService.buscarProductoXID(id);
        if (productoBuscado.isPresent()) {
            return ResponseEntity.ok(productoBuscado.get());
        }
        else {
            throw new EntityNotFoundException("No existe el producto con id: "+id);
        }
    }

    @GetMapping("/random8")
    public ResponseEntity<List<Producto>> productosRandom() {
        return ResponseEntity.ok(productoService.listar8ProductosRandom());
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> guardarProducto(@RequestBody ProductoDTO productoDTO) throws BadRequestException {
        return ResponseEntity.ok(productoService.guardarProducto(productoDTO));
    }

    @PostMapping("/{id}/agregarCaracteristica")
    public ResponseEntity<String> agregarCaracteristica(@PathVariable Long id, @RequestBody Caracteristica caracteristica) throws BadRequestException {
        return ResponseEntity.ok(productoService.agregarCaracteristicaProducto(id, caracteristica));
    }

    @PutMapping
    public ResponseEntity<ProductoDTO> actualizarProducto(@RequestBody ProductoDTO productoDTO) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(productoService.actualizarProducto(productoDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Producto> productoBuscado = productoService.buscarProductoDTOXID(id);
        if (productoBuscado.isPresent()) {
            productoService.eliminarProducto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino el producto con id: "+id);
        }
        else {
            throw new EntityNotFoundException("No existe un producto con id: "+id);
        }
    }
}
