package com.proyecto.proyecto.accessingdatamysql.controller;


import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categorias")
public class CategoriaController {
    private CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> buscarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Categoria> categoriaBuscada = categoriaService.buscarCategoriaPorId(id);
        if (categoriaBuscada.isPresent()) {
            return ResponseEntity.ok(categoriaBuscada.get());
        }
        else {
            throw new EntityNotFoundException("No existe la categoria con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }

    @PutMapping
    public ResponseEntity<String> actualizarCategoria(@RequestBody Categoria categoria) {
        Optional<Categoria> categoriaBuscada = categoriaService.buscarCategoriaPorId(categoria.getIdCategoria());
        if (categoriaBuscada.isPresent()) {
            categoriaService.actualizarCategoria(categoria);
            return ResponseEntity.ok("Se actualizo la categoria con id: "+categoria.getIdCategoria());
        }
        else {
            throw new EntityNotFoundException("La categoria con id: "+categoria.getIdCategoria()+" no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Categoria> categoriaBuscada = categoriaService.buscarCategoriaPorId(id);
        if (categoriaBuscada.isPresent()) {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la categoria con id: "+id);
        }
        else {
            throw new EntityNotFoundException("No existe una categoria con id: "+id);
        }
    }

}
