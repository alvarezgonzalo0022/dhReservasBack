package com.proyecto.proyecto.accessingdatamysql.service;
import com.proyecto.proyecto.accessingdatamysql.dto.RolDTO;
import com.proyecto.proyecto.accessingdatamysql.dto.UsuarioDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.*;
import com.proyecto.proyecto.accessingdatamysql.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolService {
    private RolRepository rolRepository;
    private UsuarioRepository imagenRepository;

    private Mapper mapper;

    private UsuarioRepository usuarioRepository;

    private PoliticaRepository politicaRepository;

//    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RolService(RolRepository rolRepository, UsuarioRepository imagenRepository, UsuarioRepository usuarioRepository, Mapper mapper) {
        this.rolRepository = rolRepository;
        this.imagenRepository = imagenRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }


    public Optional<Rol> buscarRolXID(Long id) {
        return rolRepository.findById(id);
    }

    public Optional<Rol> buscarRolNombre(String nombre) {
        return rolRepository.buscarRolNombre(nombre);
    }

    public RolDTO guardarRol(RolDTO rol) throws BadRequestException {

        Set<UsuarioDTO> usuarios = rol.getUsuarios();
        Set<UsuarioDTO> usuariosGuardados = new HashSet<>();

        Optional<Rol> rolBuscado = rolRepository.buscarRolNombre(rol.getNombre());
        if (rolBuscado.isPresent()) {
            throw new EntityExistsException("Ya existe ese rol");
        }

        if (usuarios != null) {
            for (UsuarioDTO usuario : usuarios) {
                Optional<Usuario> usuarioBuscado = usuarioRepository.buscarUsuarioEmail(usuario.getEmail());
                if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol() != null)) {
                    if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol().getIdRol() == rol.getIdRol())) {
                        usuariosGuardados.add(mapper.usuarioAUsuarioDto(usuarioBuscado.get()));
                    } else {
                        throw new EntityExistsException("Este usuario ya tiene un rol distinto asignado");
                    }
                }
                else if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol() == null)) {
                    usuariosGuardados.add(mapper.usuarioAUsuarioDto(usuarioBuscado.get()));
                }
                else {
                    usuariosGuardados.add(usuario);
                }
            }
        }
        rol.setUsuarios(usuariosGuardados);
        Rol rolPorAgregar = mapper.rolDtoARol(rol);
        if (usuariosGuardados.size() > 0) {
            rolPorAgregar.asignarRol();
        }
        return mapper.rolARolDto(rolRepository.save(rolPorAgregar));
    }

    public Rol actualizarRol(Rol rol) throws BadRequestException {

        Optional<Rol> rolBuscado = rolRepository.findById(rol.getIdRol());
        if (rolBuscado.isPresent()) {
            Set<Usuario> usuarios = rol.getUsuarios();
            Set<Usuario> usuariosGuardados = new HashSet<>();

            if (usuarios != null) {
                for (Usuario usuario : usuarios) {
                    Optional<Usuario> usuarioBuscado = usuarioRepository.buscarUsuarioEmail(usuario.getEmail());
                    if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol() != null)) {
                        if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol().getIdRol() == rol.getIdRol())) {
                            usuariosGuardados.add(usuarioBuscado.get());
                        } else {
                            throw new EntityExistsException("Este usuario ya tiene un rol distinto asignado");
                        }
                    }
                    else if (usuarioBuscado.isPresent() && (usuarioBuscado.get().getRol() == null)) {
                        usuariosGuardados.add(usuarioBuscado.get());
                    }
                    else {
                        usuariosGuardados.add(usuario);
                    }
                }
            }
            rol.setUsuarios(usuariosGuardados);
            Rol rolPorAgregar = rol;
            if (usuariosGuardados.size() > 0) {
                rolPorAgregar.asignarRol();
            }
            return rolRepository.save(rolPorAgregar);
        }
        else {
            throw new EntityNotFoundException("No existe un rol con id: "+rol.getIdRol());
        }
    }

    public String agregarUsuarioRol(Long id, Usuario usuario) {
        Optional<Rol> rolBuscado = rolRepository.findById(id);
        if (rolBuscado.isPresent()) {
            Rol rolEncontrado = rolBuscado.get();
            Optional<Usuario> usuarioBuscado = usuarioRepository.buscarUsuarioEmail(usuario.getEmail());
            if (usuarioBuscado.isPresent()) {
                rolEncontrado.agregarUsuarioEnRol(usuarioBuscado.get());
                rolRepository.save(rolEncontrado);
                return "El usuario existe y se agrego correctamente al rol";
            } else {
                usuario.setIdUsuario(null);
                rolEncontrado.agregarUsuarioEnRol(usuario);
                rolRepository.save(rolEncontrado);
                return "El usuario no existia pero se creo y agrego al rol correctamente";
            }
        } else {
            throw new EntityNotFoundException("No existe el rol con id: "+id);
        }
    }


    public void eliminarRol(Long id) throws ResourceNotFoundException {
        rolRepository.deleteById(id);
    }

//    public Usuario usuarioDtoAUsuario(UsuarioDTO usuarioDto) {
//        Usuario usuario = new Usuario();
//        Ciudad ciudad = new Ciudad();
//        Rol rol = new Rol();
//        rol.setIdRol(usuarioDto.getIdRol());
////        ciudad.setIdCiudad(usuarioDto.getIdCiudad());
//        usuario.setCiudad(usuarioDto.getCiudad());
//        usuario.setIdUsuario(usuarioDto.getIdUsuario());
//        usuario.setNombre(usuarioDto.getNombre());
//        usuario.setApellido(usuarioDto.getApellido());
//        usuario.setEmail(usuarioDto.getEmail());
////        usuario.setCiudad(ciudad);
//        usuario.setRol(rol);
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        usuario.setContraseña(bCryptPasswordEncoder.encode(usuarioDto.getContraseña()));
//        return usuario;
//    }
//
//    public UsuarioDTO usuarioAUsuarioDto(Usuario usuario) {
//        UsuarioDTO usuarioDto = new UsuarioDTO();
//        usuarioDto.setIdUsuario(usuario.getIdUsuario());
//        usuarioDto.setCiudad(usuario.getCiudad());
//        usuarioDto.setNombre(usuario.getNombre());
//        usuarioDto.setApellido(usuario.getApellido());
//        usuarioDto.setEmail(usuario.getEmail());
//        usuarioDto.setContraseña(usuario.getContraseña());
////        usuarioDto.setIdCiudad(usuario.getCiudad().getIdCiudad());
//        usuarioDto.setIdRol(usuario.getRol().getIdRol());
//        return usuarioDto;
//    }

//    public RolDTO rolARolDto(Rol rol) {
//        RolDTO rolDto = new RolDTO();
//        rolDto.setIdRol(rol.getIdRol());
//        rolDto.setNombre(rol.getNombre());
//        rolDto.setUsuarios(rol.getUsuarios().stream().map(this::usuarioAUsuarioDto).collect(Collectors.toSet()));
//        return rolDto;
//    }
//
//    public Rol rolDtoARol(RolDTO rolDto) {
//        Rol rol = new Rol();
//        rol.setIdRol(rolDto.getIdRol());
//        rol.setNombre(rolDto.getNombre());
//        rol.setUsuarios(rolDto.getUsuarios().stream().map(this::usuarioDtoAUsuario).collect(Collectors.toSet()));
//        return rol;
//    }

}