package com.proyecto.proyecto.accessingdatamysql.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Categoria;
import com.proyecto.proyecto.accessingdatamysql.model.Ciudad;
import com.proyecto.proyecto.accessingdatamysql.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {
    private CiudadRepository ciudadRepository;
    @Autowired
    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    public Optional<Ciudad> buscarCiudadXID(Long id) {
        return ciudadRepository.findById(id);
    }

    public Ciudad guardarCiudad(Ciudad ciudad) {
        Optional<Ciudad> ciudadBuscada = ciudadRepository.buscarCiudadNombrePais(ciudad.getNombre(), ciudad.getPais());
        if (ciudadBuscada.isPresent()) {
            return ciudadRepository.save(ciudadBuscada.get());
        }
        else {
            return ciudadRepository.save(ciudad);
        }
    }

    public void actualizarCiudad(Ciudad ciudad) {
        ciudadRepository.save(ciudad);
    }


    public void eliminarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }


}
