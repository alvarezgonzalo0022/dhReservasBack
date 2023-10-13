package com.proyecto.proyecto.accessingdatamysql.service;
import com.proyecto.proyecto.accessingdatamysql.dto.ClienteDTO;
import com.proyecto.proyecto.accessingdatamysql.dto.UsuarioDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.CustomConstraintViolationException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.*;
import com.proyecto.proyecto.accessingdatamysql.model.Cliente;
import com.proyecto.proyecto.accessingdatamysql.repository.RolRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    private Mapper mapper;
    private ProductoService productoService;

    private RolRepository rolRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ProductoService productoService, RolRepository rolRepository, Mapper mapper) {
        this.clienteRepository = clienteRepository;
        this.productoService = productoService;
        this.rolRepository = rolRepository;
        this.mapper = mapper;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClienteXID(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarClienteEmail(String email) {
        return clienteRepository.buscarClienteEmail(email);
    }

    public String guardarCliente(ClienteDTO clienteDto) throws BadRequestException {
        Optional<Cliente> clienteBuscado = clienteRepository.buscarClienteEmail(clienteDto.getEmail());
        if (clienteBuscado.isPresent()) {
            throw new EntityExistsException("Este email ya tiene un cliente creado");
        }
        else {
            Optional<Rol> rolBuscado = rolRepository.buscarRolNombre("Cliente");
            if (rolBuscado.isPresent()) {
                clienteDto.setIdRol(rolBuscado.get().getIdRol());
                if (!Objects.equals(clienteDto.getContraseña(), "") && clienteDto.getContraseña() != null) {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    clienteDto.setContraseña(bCryptPasswordEncoder.encode(clienteDto.getContraseña()));
                }
                clienteRepository.save(mapper.clienteDtoACliente(clienteDto));
                return "Cliente creado con exito";
            }
            else {
                throw new EntityNotFoundException("No existe el rol Cliente, debe ser creado para poder crear un cliente");
            }
        }
    }

    public String actualizarCliente(ClienteDTO clienteDto) throws ResourceNotFoundException {
        Optional<Cliente> clienteBuscado = buscarClienteXID(clienteDto.getIdUsuario());
        if (clienteBuscado.isPresent()){
            clienteDto.setIdRol(clienteBuscado.get().getRol().getIdRol());
            clienteRepository.save(mapper.clienteDtoACliente(clienteDto));
            return "Cliente actualizado con exito";
        }
        else{
            throw new EntityNotFoundException("No existe el cliente con id: "+clienteDto.getIdUsuario());
        }
    }


    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }


//    public Cliente clienteDtoACliente(ClienteDTO clienteDto) {
//        Cliente cliente = new Cliente();
//        Ciudad ciudad = new Ciudad();
//        Rol rol = new Rol();
//        rol.setIdRol(clienteDto.getIdRol());
////        ciudad.setIdCiudad(clienteDto.getIdCiudad());
//        cliente.setCiudad(clienteDto.getCiudad());
//        cliente.setIdUsuario(clienteDto.getIdUsuario());
//        cliente.setNombre(clienteDto.getNombre());
//        cliente.setApellido(clienteDto.getApellido());
//        cliente.setEmail(clienteDto.getEmail());
//        cliente.setContraseña(clienteDto.getContraseña());
////        cliente.setCiudad(ciudad);
//        cliente.setRol(rol);
//        return cliente;
//    }
//
//    public ClienteDTO clienteAClienteDto(Cliente cliente) {
//        ClienteDTO clienteDto = new ClienteDTO();
//        clienteDto.setIdUsuario(cliente.getIdUsuario());
//        cliente.setCiudad(cliente.getCiudad());
//        clienteDto.setNombre(cliente.getNombre());
//        clienteDto.setApellido(cliente.getApellido());
//        clienteDto.setEmail(cliente.getEmail());
//        clienteDto.setContraseña(cliente.getContraseña());
////        clienteDto.setIdCiudad(cliente.getCiudad().getIdCiudad());
//        clienteDto.setIdRol(cliente.getRol().getIdRol());
//        return clienteDto;
//    }

}
