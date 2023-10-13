package com.proyecto.proyecto.accessingdatamysql.service;

import com.proyecto.proyecto.accessingdatamysql.model.Politica;
import com.proyecto.proyecto.accessingdatamysql.model.Politica;
import com.proyecto.proyecto.accessingdatamysql.repository.PoliticaRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.PoliticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoliticaService {

    private PoliticaRepository politicaRepository;
    @Autowired
    public PoliticaService(PoliticaRepository politicaRepository) {
        this.politicaRepository = politicaRepository;
    }

    public List<Politica> listarPoliticas() {
        return politicaRepository.findAll();
    }

    public Optional<Politica> buscarPoliticaXID(Long id) {
        return politicaRepository.findById(id);
    }

    public Optional<Politica> buscarPoliticaCompletaNombre(String nombre) {
        System.out.println(nombre);
        return politicaRepository.buscarPoliticaCompletaNombre(nombre);
    }

    public Politica guardarPolitica(Politica politica) {
        Optional<Politica> politicaBuscada = politicaRepository.buscarPoliticaCompletaNombre(politica.getNombre());
        if (politicaBuscada.isPresent()) {
            return politicaRepository.save(politicaBuscada.get());
        }
        else {
            return politicaRepository.save(politica);
        }
    }

    public void actualizarPolitica(Politica politica, Politica politicaBuscada) {
        politicaBuscada.setNombre(politica.getNombre());
        politicaRepository.save(politicaBuscada);
    }

//
    public void eliminarPolitica(Long id) {
        politicaRepository.deleteById(id);
    }

}
