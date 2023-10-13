package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Politica;
import com.proyecto.proyecto.accessingdatamysql.service.PoliticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/politicas")
public class PoliticaController {

    private PoliticaService politicaService;

    @Autowired
    public PoliticaController(PoliticaService politicaService) {
        this.politicaService = politicaService;
    }

    @GetMapping
    public ResponseEntity<List<Politica>> buscarPoliticas() {
        return ResponseEntity.ok(politicaService.listarPoliticas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Politica> buscarPolitica(@PathVariable Long id) {
        Optional<Politica> politicaBuscada = politicaService.buscarPoliticaXID(id);
        if (politicaBuscada.isPresent()) {
            return ResponseEntity.ok(politicaBuscada.get());
        } else {
            throw new EntityNotFoundException("No existe la politica con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<Politica> guardarPolitica(@RequestBody Politica politica) {
        return ResponseEntity.ok(politicaService.guardarPolitica(politica));
    }

    @PutMapping
    public ResponseEntity<String> actualizarPolitica(@RequestBody Politica politica) {
        Optional<Politica> politicaBuscada = politicaService.buscarPoliticaXID(politica.getIdPolitica());
        if (politicaBuscada.isPresent()) {
            politicaService.actualizarPolitica(politica, politicaBuscada.get());
            return ResponseEntity.ok("Se actualizo la politica con id: " + politica.getIdPolitica());
        } else {
            throw new EntityNotFoundException("No existe la politica con id: " + politica.getIdPolitica());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPolitica(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Politica> politicaBuscada = politicaService.buscarPoliticaXID(id);
        if (politicaBuscada.isPresent()) {
            politicaService.eliminarPolitica(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la politica con id: " + id);
        } else {
            throw new EntityNotFoundException("No existe la politica con id: " + id);
        }
    }
}
