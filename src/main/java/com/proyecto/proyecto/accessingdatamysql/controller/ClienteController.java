package com.proyecto.proyecto.accessingdatamysql.controller;
import com.proyecto.proyecto.accessingdatamysql.dto.ClienteDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Cliente;
import com.proyecto.proyecto.accessingdatamysql.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        Optional<Cliente> clienteBuscado = clienteService.buscarClienteXID(id);
        if (clienteBuscado.isPresent()){
            return ResponseEntity.ok(clienteBuscado.get());
        }
        else{
            throw new EntityNotFoundException("No existe el cliente con id: "+id);
        }
    }

    @GetMapping("/buscarClienteEmail/{email}")
    public ResponseEntity<Cliente> buscarClienteEmail(@PathVariable String email) {
        Optional<Cliente> clienteBuscado = clienteService.buscarClienteEmail(email);
        if (clienteBuscado.isPresent()){
            return ResponseEntity.ok(clienteBuscado.get());
        }
        else{
            throw new EntityNotFoundException("No existe el cliente con email: "+email);
        }
    }

    @PostMapping
    public ResponseEntity<String> guardarCliente(@RequestBody ClienteDTO clienteDto) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(clienteService.guardarCliente(clienteDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> actualizarCliente(@RequestBody ClienteDTO clienteDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(clienteService.actualizarCliente(clienteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Cliente> clienteBuscado = clienteService.buscarClienteXID(id);
        if (clienteBuscado.isPresent()){
            clienteService.eliminarCliente(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino el cliente con id: "+id);
        }
        else{
            throw new EntityNotFoundException("No existe una cliente con id: "+id);
        }
    }
}
