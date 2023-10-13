package com.proyecto.proyecto.accessingdatamysql.model;

import com.proyecto.proyecto.accessingdatamysql.dto.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Mapper() {
    }

    //Cliente Mapper

    public Cliente clienteDtoACliente(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();
        Ciudad ciudad = new Ciudad();
        Rol rol = new Rol();
        rol.setIdRol(clienteDto.getIdRol());
//        ciudad.setIdCiudad(clienteDto.getIdCiudad());
        cliente.setCiudad(clienteDto.getCiudad());
        cliente.setIdUsuario(clienteDto.getIdUsuario());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setContraseña(clienteDto.getContraseña());
//        cliente.setCiudad(ciudad);
        cliente.setRol(rol);
        return cliente;
    }

    public ClienteDTO clienteAClienteDto(Cliente cliente) {
        ClienteDTO clienteDto = new ClienteDTO();
        clienteDto.setIdUsuario(cliente.getIdUsuario());
        cliente.setCiudad(cliente.getCiudad());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setApellido(cliente.getApellido());
        clienteDto.setEmail(cliente.getEmail());
        clienteDto.setContraseña(cliente.getContraseña());
//        clienteDto.setIdCiudad(cliente.getCiudad().getIdCiudad());
        clienteDto.setIdRol(cliente.getRol().getIdRol());
        return clienteDto;
    }

    //Imagen Mapper

    public Imagen imagenDTOAImagen (ImagenDTO imagenDTO) {
        Imagen imagen = new Imagen();
        Producto producto = new Producto();
        producto.setIdProducto(imagenDTO.getIdProducto());
        imagen.setTitulo(imagenDTO.getTitulo());
        imagen.setImage(imagenDTO.getImage());
        imagen.setProducto(producto);

        return imagen;
    }

    public ImagenDTO imagenAImagenDTO (Imagen imagen) {
        ImagenDTO imagenDto = new ImagenDTO();
        imagenDto.setId(imagen.getId());
        imagenDto.setTitulo(imagen.getTitulo());
        imagenDto.setImage(imagen.getImage());
        imagenDto.setIdProducto(imagen.getProducto().getIdProducto());

        return imagenDto;
    }

    //Producto Mapper

    public ProductoDTO productoAProductoDTO (Producto producto) {
        ProductoDTO productoDTORespuesta = new ProductoDTO();
        productoDTORespuesta.setIdProducto(producto.getIdProducto());
        productoDTORespuesta.setTitulo(producto.getTitulo());
        productoDTORespuesta.setDescripcion(producto.getDescripcion());
        productoDTORespuesta.setCategoria(producto.getCategoria().getIdCategoria());
        productoDTORespuesta.setCiudad(producto.getCiudad().getIdCiudad());
        productoDTORespuesta.setCaracteristicas(producto.getCaracteristicas());
        productoDTORespuesta.setPoliticas(producto.getPoliticas());
        productoDTORespuesta.setPrecio(producto.getPrecio());
        productoDTORespuesta.setValoracionDeSeguridad(producto.getValoracionDeSeguridad());
        System.out.println(producto.getImagenes());
        Set<ImagenDTO> imagenesDto = producto.getImagenes().stream().map(this::imagenAImagenDTO).collect(Collectors.toSet());
        System.out.println(imagenesDto);
        productoDTORespuesta.setImagenes(imagenesDto);

        return productoDTORespuesta;
    }

    public Producto productoDTOAProducto (ProductoDTO productoDTO) {;
//        Producto productoDTOACambiar = mapper.convertValue(productoDTO, Producto.class);
//        System.out.println(productoDTOACambiar);
        Producto producto = new Producto();
        Ciudad ciudad = new Ciudad();
        Categoria categoria = new Categoria();
        producto.setIdProducto(productoDTO.getIdProducto());
        ciudad.setIdCiudad(productoDTO.getCiudad());
        categoria.setIdCategoria(productoDTO.getCategoria());
        producto.setTitulo(productoDTO.getTitulo());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoria(categoria);
        producto.setCiudad(ciudad);
        producto.setValoracionDeSeguridad(productoDTO.getValoracionDeSeguridad());
        Set<Caracteristica> caracteristicas = productoDTO.getCaracteristicas();
        Set<Caracteristica> caracteristicasPorGuardar = new HashSet<>();
        if (caracteristicas != null) {
            for (Caracteristica caracteristica: caracteristicas) {
                caracteristicasPorGuardar.add(caracteristica);
            }
        }
        producto.setCaracteristicas(caracteristicasPorGuardar);
        Set<Politica> politicas = productoDTO.getPoliticas();
        Set<Politica> politicasPorGuardar = new HashSet<>();
        if (politicas != null) {
            for (Politica politica: politicas) {
                politicasPorGuardar.add(politica);
            }
        }
        producto.setPoliticas(politicasPorGuardar);
        Set<ImagenDTO> imagenesDTO = productoDTO.getImagenes();
        Set<Imagen> imagenes = new HashSet<>();
        if (imagenesDTO != null) {
            imagenes = imagenesDTO.stream().map(imagenDTO -> imagenDTOAImagen(imagenDTO)).collect(Collectors.toSet());
        }
        System.out.println(imagenes);
        producto.setImagenes(imagenes);

        return producto;
    }

    //Reserva Mapper

    public ReservaDTO reservaAReservaDTO(Reserva reserva) {
        ReservaDTO reservaDto = new ReservaDTO();
        reservaDto.setIdReserva(reserva.getIdReserva());
        reservaDto.setHoraComienzoReserva(reserva.getHoraComienzoReserva());
        reservaDto.setFechaInicialReserva(reserva.getFechaInicialReserva());
        reservaDto.setFechaFinalReserva(reserva.getFechaFinalReserva());
        reservaDto.setProducto(reserva.getProducto().getIdProducto());
        reservaDto.setCliente(reserva.getUsuario().getIdUsuario());
        return reservaDto;
    }

    public Reserva reservaDTOAReserva(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();
        Producto producto = new Producto();
        producto.setIdProducto(reservaDTO.getProducto());
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(reservaDTO.getCliente());
        reserva.setIdReserva(reservaDTO.getIdReserva());
        reserva.setHoraComienzoReserva(reservaDTO.getHoraComienzoReserva());
        reserva.setFechaInicialReserva(reservaDTO.getFechaInicialReserva());
        reserva.setFechaFinalReserva(reservaDTO.getFechaFinalReserva());
        reserva.setUsuario(cliente);
        reserva.setProducto(producto);
        return reserva;
    }

    //Rol Mapper

    public RolDTO rolARolDto(Rol rol) {
        RolDTO rolDto = new RolDTO();
        rolDto.setIdRol(rol.getIdRol());
        rolDto.setNombre(rol.getNombre());
        rolDto.setUsuarios(rol.getUsuarios().stream().map(this::usuarioAUsuarioDto).collect(Collectors.toSet()));
        return rolDto;
    }

    public Rol rolDtoARol(RolDTO rolDto) {
        Rol rol = new Rol();
        rol.setIdRol(rolDto.getIdRol());
        rol.setNombre(rolDto.getNombre());
        rol.setUsuarios(rolDto.getUsuarios().stream().map(this::usuarioDtoAUsuario).collect(Collectors.toSet()));
        return rol;
    }

    //Usuario Mapper

    public Usuario usuarioDtoAUsuario(UsuarioDTO usuarioDto) {
        Usuario usuario = new Usuario();
        Ciudad ciudad = new Ciudad();
        Rol rol = new Rol();
        rol.setIdRol(usuarioDto.getIdRol());
//        ciudad.setIdCiudad(usuarioDto.getIdCiudad());
        usuario.setIdUsuario(usuarioDto.getIdUsuario());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setContraseña(usuarioDto.getContraseña());
//        usuario.setCiudad(ciudad);
        usuario.setRol(rol);
        return usuario;
    }

    public UsuarioDTO usuarioAUsuarioDto(Usuario usuario) {
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setIdUsuario(usuario.getIdUsuario());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellido(usuario.getApellido());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setContraseña(usuario.getContraseña());
//        usuarioDto.setIdCiudad(usuario.getCiudad().getIdCiudad());
        usuarioDto.setIdRol(usuario.getRol().getIdRol());
        return usuarioDto;
    }
}
