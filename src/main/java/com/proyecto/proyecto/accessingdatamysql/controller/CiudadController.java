package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Ciudad;
import com.proyecto.proyecto.accessingdatamysql.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ciudades")
public class CiudadController {

    private CiudadService ciudadService;

    @Autowired
    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @GetMapping
    public ResponseEntity<List<Ciudad>> buscarCiudades() {
        return ResponseEntity.ok(ciudadService.listarCiudades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> buscarCiudad(@PathVariable Long id) {
        Optional<Ciudad> ciudadBuscada = ciudadService.buscarCiudadXID(id);
        if (ciudadBuscada.isPresent()){
            return ResponseEntity.ok(ciudadBuscada.get());
        }
        else{
            throw new EntityNotFoundException("No existe la ciudad con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<Ciudad> guardarCiudad(@RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(ciudadService.guardarCiudad(ciudad));
    }

    @PutMapping
    public ResponseEntity<String> actualizarCiudad(@RequestBody Ciudad ciudad) {
        Optional<Ciudad> ciudadBuscada = ciudadService.buscarCiudadXID(ciudad.getIdCiudad());
        if (ciudadBuscada.isPresent()){
            ciudadService.actualizarCiudad(ciudad);
            return ResponseEntity.ok("Se actualizo la ciudad con id: "+ciudad.getIdCiudad());
        }
        else{
            throw new EntityNotFoundException("No existe la ciudad con id: "+ciudad.getIdCiudad());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCiudad(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Ciudad> ciudadBuscada = ciudadService.buscarCiudadXID(id);
        if (ciudadBuscada.isPresent()){
            ciudadService.eliminarCiudad(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la ciudad con id: "+id);
        }
        else{
            throw new EntityNotFoundException("No existe la ciudad con id: "+id);
        }
    }
}
