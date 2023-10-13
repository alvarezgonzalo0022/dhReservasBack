package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Caracteristica;
import com.proyecto.proyecto.accessingdatamysql.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/caracteristicas")
public class CaracteristicaController {

    private CaracteristicaService caracteristicaService;

    @Autowired
    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping
    public ResponseEntity<List<Caracteristica>> buscarCaracteristicas() {
        return ResponseEntity.ok(caracteristicaService.listarCaracteristicas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caracteristica> buscarCaracteristica(@PathVariable Long id) {
        Optional<Caracteristica> caracteristicaBuscada = caracteristicaService.buscarCaracteristicaXID(id);
        if (caracteristicaBuscada.isPresent()) {
            return ResponseEntity.ok(caracteristicaBuscada.get());
        } else {
            throw new EntityNotFoundException("No existe la caracteristica con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<Caracteristica> guardarCaracteristica(@RequestBody Caracteristica caracteristica) {
        return ResponseEntity.ok(caracteristicaService.guardarCaracteristica(caracteristica));
    }

    @PutMapping
    public ResponseEntity<String> actualizarCaracteristica(@RequestBody Caracteristica caracteristica) {
        Optional<Caracteristica> caracteristicaBuscada = caracteristicaService.buscarCaracteristicaXID(caracteristica.getIdCaracteristica());
        if (caracteristicaBuscada.isPresent()) {
            caracteristicaService.actualizarCaracteristica(caracteristica, caracteristicaBuscada.get());
            return ResponseEntity.ok("Se actualizo la caracteristica con id: " + caracteristica.getIdCaracteristica());
        } else {
            throw new EntityNotFoundException("No existe la caracteristica con id: " + caracteristica.getIdCaracteristica());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCaracteristica(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Caracteristica> caracteristicaBuscada = caracteristicaService.buscarCaracteristicaXID(id);
        if (caracteristicaBuscada.isPresent()) {
            caracteristicaService.eliminarCaracteristica(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la caracteristica con id: " + id);
        } else {
            throw new EntityNotFoundException("No existe la caracteristica con id: " + id);
        }
    }
}
