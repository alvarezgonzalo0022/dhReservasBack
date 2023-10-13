package com.proyecto.proyecto.accessingdatamysql.service;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        Optional<Categoria> categoriaBuscada = categoriaRepository.buscarCategoriaTitulo(categoria.getTitulo());
        if (categoriaBuscada.isPresent()) {
            return categoriaRepository.save(categoriaBuscada.get());
        }
        else {
            return categoriaRepository.save(categoria);
        }
    }

    public void actualizarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }


    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

}
