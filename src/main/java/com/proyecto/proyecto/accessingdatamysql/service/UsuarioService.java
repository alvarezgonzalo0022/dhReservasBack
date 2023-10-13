package com.proyecto.proyecto.accessingdatamysql.service;
import com.proyecto.proyecto.accessingdatamysql.dto.UsuarioDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.*;
import com.proyecto.proyecto.accessingdatamysql.repository.RolRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private ProductoService productoService;

    private RolRepository rolRepository;

    private Mapper mapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ProductoService productoService, Mapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.productoService = productoService;
        this.mapper = mapper;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioXID(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDto) throws BadRequestException {
        Optional<Usuario> usuarioBuscado = usuarioRepository.buscarUsuarioEmail(usuarioDto.getEmail());
        if (usuarioBuscado.isPresent()) {
            throw new EntityExistsException("Este email ya tiene un usuario creado");
        }
        else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            usuarioDto.setContraseña(bCryptPasswordEncoder.encode(usuarioDto.getContraseña()));
            return mapper.usuarioAUsuarioDto(usuarioRepository.save(mapper.usuarioDtoAUsuario(usuarioDto)));
        }
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDto) throws ResourceNotFoundException {
        Optional<Usuario> usuarioBuscado = buscarUsuarioXID(usuarioDto.getIdUsuario());
        if (usuarioBuscado.isPresent()){
            return mapper.usuarioAUsuarioDto(usuarioRepository.save(mapper.usuarioDtoAUsuario(usuarioDto)));
        }
        else{
            throw new EntityNotFoundException("No existe el usuario con id: "+usuarioDto.getIdUsuario());
        }
    }


    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

//    public Usuario usuarioDtoAUsuario(UsuarioDTO usuarioDto) {
//        Usuario usuario = new Usuario();
//        Ciudad ciudad = new Ciudad();
//        Rol rol = new Rol();
//        rol.setIdRol(usuarioDto.getIdRol());
////        ciudad.setIdCiudad(usuarioDto.getIdCiudad());
//        usuario.setIdUsuario(usuarioDto.getIdUsuario());
//        usuario.setNombre(usuarioDto.getNombre());
//        usuario.setApellido(usuarioDto.getApellido());
//        usuario.setEmail(usuarioDto.getEmail());
//        usuario.setContraseña(usuarioDto.getContraseña());
////        usuario.setCiudad(ciudad);
//        usuario.setRol(rol);
//        return usuario;
//    }
//
//    public UsuarioDTO usuarioAUsuarioDto(Usuario usuario) {
//        UsuarioDTO usuarioDto = new UsuarioDTO();
//        usuarioDto.setIdUsuario(usuario.getIdUsuario());
//        usuarioDto.setNombre(usuario.getNombre());
//        usuarioDto.setApellido(usuario.getApellido());
//        usuarioDto.setEmail(usuario.getEmail());
//        usuarioDto.setContraseña(usuario.getContraseña());
////        usuarioDto.setIdCiudad(usuario.getCiudad().getIdCiudad());
//        usuarioDto.setIdRol(usuario.getRol().getIdRol());
//        return usuarioDto;
//    }

}
