package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.dto.UsuarioDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Usuario;
import com.proyecto.proyecto.accessingdatamysql.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioBuscado = usuarioService.buscarUsuarioXID(id);
        if (usuarioBuscado.isPresent()){
            return ResponseEntity.ok(usuarioBuscado.get());
        }
        else{
            throw new EntityNotFoundException("No existe el usuario con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> guardarUsuario(@RequestBody UsuarioDTO usuarioDto) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuarioDto));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestBody UsuarioDTO usuarioDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Usuario> usuarioBuscado = usuarioService.buscarUsuarioXID(id);
        if (usuarioBuscado.isPresent()){
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino el usuario con id: "+id);
        }
        else{
            throw new EntityNotFoundException("No existe una usuario con id: "+id);
        }
    }
}
