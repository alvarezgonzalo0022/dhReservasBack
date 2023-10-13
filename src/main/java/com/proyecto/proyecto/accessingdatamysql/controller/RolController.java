package com.proyecto.proyecto.accessingdatamysql.controller;
import com.proyecto.proyecto.accessingdatamysql.dto.RolDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Usuario;
import com.proyecto.proyecto.accessingdatamysql.model.Rol;
import com.proyecto.proyecto.accessingdatamysql.service.UsuarioService;
import com.proyecto.proyecto.accessingdatamysql.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolController {
    private RolService rolService;
    private UsuarioService usuarioService;

    @Autowired
    public RolController(RolService rolService, UsuarioService usuarioService) {
        this.rolService = rolService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> buscarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarRol(@PathVariable Long id) {
        Optional<Rol> rolBuscado = rolService.buscarRolXID(id);
        if (rolBuscado.isPresent()) {
            return ResponseEntity.ok(rolBuscado.get());
        }
        else {
            throw new EntityNotFoundException("No existe el rol con id: "+id);
        }
    }

    @GetMapping("/buscarRolNombre/{nombre}")
    public ResponseEntity<Rol> buscarRol(@PathVariable String nombre) {
        Optional<Rol> rolBuscado = rolService.buscarRolNombre(nombre);
        if (rolBuscado.isPresent()) {
            return ResponseEntity.ok(rolBuscado.get());
        }
        else {
            throw new EntityNotFoundException("No existe el rol con nombre: "+nombre);
        }
    }


    @PostMapping
    public ResponseEntity<RolDTO> guardarRol(@RequestBody RolDTO rol) throws BadRequestException {
        return ResponseEntity.ok(rolService.guardarRol(rol));
    }

    @PostMapping("/{id}/agregarUsuario")
    public ResponseEntity<String> agregarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(rolService.agregarUsuarioRol(id, usuario));
    }

    @PutMapping
    public ResponseEntity<Rol> actualizarRol(@RequestBody Rol rol) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(rolService.actualizarRol(rol));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Rol> rolBuscado = rolService.buscarRolXID(id);
        if (rolBuscado.isPresent()) {
            rolService.eliminarRol(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino el rol con id: "+id);
        }
        else {
            throw new EntityNotFoundException("No existe un rol con id: "+id);
        }
    }
}
