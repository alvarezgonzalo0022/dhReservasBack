package com.proyecto.proyecto.accessingdatamysql.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CaracteristicaService {

    private CaracteristicaRepository caracteristicaRepository;
    @Autowired
    public CaracteristicaService (CaracteristicaRepository caracteristicaRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
    }

    public List<Caracteristica> listarCaracteristicas() {
        return caracteristicaRepository.findAll();
    }

    public Optional<Caracteristica> buscarCaracteristicaXID(Long id) {
        return caracteristicaRepository.findById(id);
    }

    public Optional<Caracteristica> buscarCaracteristicaXNombre(String nombre) {
        return caracteristicaRepository.buscarCaracteristicaNombre(nombre);
    }

    public Optional<Caracteristica> buscarCaracteristicaCompletaNombre(String nombre) {
        return caracteristicaRepository.buscarCaracteristicaCompletaNombre(nombre);
    }

    public Caracteristica guardarCaracteristica(Caracteristica caracteristica) {
        Optional<Caracteristica> caracteristicaBuscada = caracteristicaRepository.buscarCaracteristicaCompletaNombre(caracteristica.getNombre());
        if (caracteristicaBuscada.isPresent()) {
            return caracteristicaRepository.save(caracteristicaBuscada.get());
        }
        else {
            return caracteristicaRepository.save(caracteristica);
        }
    }

    public void actualizarCaracteristica(Caracteristica caracteristica, Caracteristica caracteristicaBuscada) {
        caracteristicaBuscada.setNombre(caracteristica.getNombre());
        caracteristicaRepository.save(caracteristicaBuscada);
    }

//
    public void eliminarCaracteristica(Long id) {
        caracteristicaRepository.deleteById(id);
    }

}
