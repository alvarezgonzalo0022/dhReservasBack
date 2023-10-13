package com.proyecto.proyecto.accessingdatamysql.service;


import com.proyecto.proyecto.accessingdatamysql.dto.ImagenDTO;
import com.proyecto.proyecto.accessingdatamysql.dto.ProductoDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.*;
import com.proyecto.proyecto.accessingdatamysql.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private ProductoRepository productoRepository;
    private ImagenRepository imagenRepository;

    private ProductoSearchImpl productoSearchImpl;

    private Mapper mapper;

    private Validator validator;

    private CaracteristicaRepository caracteristicaRepository;

    private PoliticaRepository politicaRepository;

//    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ImagenRepository imagenRepository, CaracteristicaRepository caracteristicaRepository, ProductoSearchImpl productoSearchImpl, Mapper mapper, Validator validator) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.productoSearchImpl = productoSearchImpl;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<Producto> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos;
    }

    public PaginaProductosResponse listarProductosPaginados(String start) {
        int limit = 8;
        List<Producto> productos = productoSearchImpl.listarProductosPaginados(start, limit);
        String nuevoStart = generarStart(productos);
        Boolean hasMore = !productos.isEmpty() && (productos.size() == limit);
        Next next = new Next("http://ec2-18-220-171-146.us-east-2.compute.amazonaws.com:8080/productos/paginados?start="+nuevoStart, nuevoStart);
        return new PaginaProductosResponse(next, hasMore, productos);
    }

    public PaginaProductosResponse filtrarProductosPaginados(String start, String titulo, String categoria, String ciudad) {
        int limit = 8;
        List<Producto> productos = productoSearchImpl.filtrarProductosPaginados(start, limit, titulo, categoria, ciudad);
        String nuevoStart = generarStart(productos);
        Boolean hasMore = !productos.isEmpty() && (productos.size() == limit);
        Next next = new Next("http://ec2-18-220-171-146.us-east-2.compute.amazonaws.com:8080/productos/filtrarProductosPaginados?start="+nuevoStart+"&titulo="+titulo+"&categoria="+categoria+"&ciudad="+ciudad, nuevoStart);
        return new PaginaProductosResponse(next, hasMore, productos);
    }

    public String generarStart(List<Producto> productos) {
        if (productos.size() > 0) {
            Producto ultimoProducto = productos.get(productos.size() - 1);
            return String.valueOf(ultimoProducto.getIdProducto());
        }
        else {
            return "null";
        }
    }

    public List<Producto> listarProductosPorCiudad(String nombreCiudad) {
        List<Producto> productos = productoRepository.filtrarProductosPorCiudad(nombreCiudad);
        return productos;
    }

    public List<Producto> listarProductosPorRangoDeFechas(String fechaInicial, String fechaFinal) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicialDate = formatter.parse(fechaInicial);
        Date fechFinalDate = formatter.parse(fechaFinal);

        return productoRepository.filtrarProductosPorRangoDeFechas(fechaInicialDate, fechFinalDate);
    }

    public List<Producto> listarProductosPorCiudadYRangoDeFechas(String nombreCiudad, String fechaInicial, String fechaFinal) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicialDate = formatter.parse(fechaInicial);
        Date fechaFinalDate = formatter.parse(fechaFinal);

        return productoRepository.filtrarProductosPorCiudadYRangoDeFechas(nombreCiudad, fechaInicialDate, fechaFinalDate);
    }

    public List<Producto> buscar(String titulo, String categoria, String ciudad) throws BadRequestException{
        return productoSearchImpl.filtrarPorCiudadCategoriaTitulo(titulo, categoria, ciudad);
    }

    public List<Producto> listarProductosPorCategoria(String tituloCategoria) {
        List<Producto> productos = productoRepository.filtrarProductosPorCategoria(tituloCategoria);
//        List<ProductoDTO> productosDto = new ArrayList<>();
//        for (Producto producto:productos) {
//            productosDto.add(productoAProductoDTO(producto));
//        }
        return productos;
    }

    public Optional<Producto> buscarProductoDTOXID(Long id) {
        return productoRepository.findById(id);
    }

    public Optional<Integer> buscarCaracteristicaXNombre(String nombre) {
        return productoRepository.buscarCaracteristicaNombre(nombre);
    }

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) throws BadRequestException {

        productoDTO = validator.validarProductoDTO(productoDTO);

        Set<Caracteristica> caracteristicas = productoDTO.getCaracteristicas();
        Set<Caracteristica> caracteristicasGuardadas = new HashSet<>();
        if (caracteristicas != null) {
            for (Caracteristica caracteristica : caracteristicas) {
                Optional<Caracteristica> caracteristicaBuscada = caracteristicaRepository.buscarCaracteristicaCompletaNombre(caracteristica.getNombre());
                if (caracteristicaBuscada.isPresent()) {
                    System.out.println("CARACTERISTICA BUSCADA");
                    System.out.println(caracteristicaBuscada);
                    caracteristicasGuardadas.add(caracteristicaBuscada.get());
                }
                else {
//                    throw new RuntimeException("No existe una caracteristica con id: " + caracteristica.getIdCaracteristica());
                    Caracteristica caracteristicaNueva = new Caracteristica(caracteristica.getNombre(), caracteristica.getIcono());
                    caracteristicasGuardadas.add(caracteristicaNueva);
                }
            }
        }
        Set<Politica> politicas = productoDTO.getPoliticas();
        Set<Politica> politicasGuardadas = new HashSet<>();
        if (politicas != null) {
            for (Politica politica : politicas) {
                Optional<Politica> politicaBuscada = politicaRepository.buscarPoliticaCompletaNombre(politica.getNombre());
                if (politicaBuscada.isPresent()) {
                    System.out.println("CARACTERISTICA BUSCADA");
                    System.out.println(politicaBuscada);
                    politicasGuardadas.add(politicaBuscada.get());
                }
                else {
//                    throw new RuntimeException("No existe una politica con id: " + politica.getIdPolitica());
                    Politica politicaNueva = new Politica(politica.getNombre(), politica.getDescripcion());
                    politicasGuardadas.add(politicaNueva);
                }
            }
        }
        Set<ImagenDTO> imagenesDto = productoDTO.getImagenes();
        Set<ImagenDTO> imagenesDtoGuardadas = new HashSet<>();
        if (imagenesDto != null) {
            for (ImagenDTO imagenDTO : imagenesDto) {
                Optional<Imagen> imagenBuscada = imagenRepository.buscarImagenUrl(imagenDTO.getImage());
                if (imagenBuscada.isPresent() && (imagenBuscada.get().getProducto() != null)) {
//                    imagenesDtoGuardadas.add(imagenAImagenDTO(imagenBuscada.get()));
                    throw new EntityExistsException("Esta imagen ya pertenece a otro producto");
                }
                else if (imagenBuscada.isPresent() && (imagenBuscada.get().getProducto() == null)) {
//                    Imagen imagenNueva = new Imagen(imagenBuscada.get().getTitulo(), imagenBuscada.get().getUrl());
                    imagenesDtoGuardadas.add(mapper.imagenAImagenDTO(imagenBuscada.get()));
                }
                else {
                    ImagenDTO imagenNueva = new ImagenDTO(imagenDTO.getTitulo(), imagenDTO.getImage());
                    imagenesDtoGuardadas.add(imagenNueva);
                }
            }
        }
        productoDTO.setPoliticas(politicasGuardadas);
        productoDTO.setCaracteristicas(caracteristicasGuardadas);
        productoDTO.setImagenes(imagenesDtoGuardadas);
//        productoDTO.setImagenes(imagenesDtoGuardadas);
        Producto productoPorAgregar = mapper.productoDTOAProducto(productoDTO);
//        Set<Imagen> setVacio = new HashSet<>();
//        productoPorAgregar.setImagenes(setVacio);
        if (imagenesDtoGuardadas.size() > 0) {
            productoPorAgregar.asignarProducto();
        }
        System.out.println("PRODUCTO POR AGREGAR");
        System.out.println(productoPorAgregar);
        return mapper.productoAProductoDTO(productoRepository.save(productoPorAgregar));
    }

    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) throws BadRequestException {

        productoDTO = validator.validarProductoDTO(productoDTO);

        Optional<Producto> productoBuscado = productoRepository.findById(productoDTO.getIdProducto());
        if (productoBuscado.isPresent()) {
            Set<Caracteristica> caracteristicas = productoDTO.getCaracteristicas();
            System.out.println(caracteristicas);
            Set<Caracteristica> caracteristicasGuardadas = new HashSet<>();
            if (caracteristicas != null) {
                for (Caracteristica caracteristica: caracteristicas) {
                    Optional<Caracteristica> caracteristicaBuscada = caracteristicaRepository.buscarCaracteristicaCompletaNombre(caracteristica.getNombre());
                    if (caracteristicaBuscada.isPresent()) {
                        System.out.println("CARACTERISTICA BUSCADA");
                        System.out.println(caracteristicaBuscada);
                        caracteristicasGuardadas.add(caracteristicaBuscada.get());
                    }
                    else {
                        Caracteristica caracteristicaNueva = new Caracteristica(caracteristica.getNombre(),caracteristica.getIcono());
                        caracteristicasGuardadas.add(caracteristicaNueva);
                    }
                }
            }
            Set<Politica> politicas = productoDTO.getPoliticas();
            Set<Politica> politicasGuardadas = new HashSet<>();
            if (politicas != null) {
                for (Politica politica : politicas) {
                    Optional<Politica> politicaBuscada = politicaRepository.buscarPoliticaCompletaNombre(politica.getNombre());
                    if (politicaBuscada.isPresent()) {
                        System.out.println("CARACTERISTICA BUSCADA");
                        System.out.println(politicaBuscada);
                        politicasGuardadas.add(politicaBuscada.get());
                    }
                    else {
//                    throw new RuntimeException("No existe una politica con id: " + politica.getIdPolitica());
                        Politica politicaNueva = new Politica(politica.getNombre(), politica.getDescripcion());
                        politicasGuardadas.add(politicaNueva);
                    }
                }
            }
            Set<ImagenDTO> imagenesDto = productoDTO.getImagenes();
            Set<ImagenDTO> imagenesDtoGuardadas = new HashSet<>();
            if (imagenesDto != null) {
                for (ImagenDTO imagenDTO : imagenesDto) {
                    Optional<Imagen> imagenBuscada = imagenRepository.buscarImagenUrl(imagenDTO.getImage());
                    if (imagenBuscada.isPresent() && (imagenBuscada.get().getProducto() != null)) {
                        if (imagenBuscada.isPresent() && (imagenBuscada.get().getProducto().getIdProducto() == productoDTO.getIdProducto())) {
                            imagenesDtoGuardadas.add(mapper.imagenAImagenDTO(imagenBuscada.get()));
                        } else {
                            throw new EntityExistsException("Esta imagen ya pertenece a otro producto");
                        }
//                    imagenesDtoGuardadas.add(mapper.imagenAImagenDTO(imagenBuscada.get()));
                    }
                    else if (imagenBuscada.isPresent() && (imagenBuscada.get().getProducto() == null)) {
//                    Imagen imagenNueva = new Imagen(imagenBuscada.get().getTitulo(), imagenBuscada.get().getUrl());
                        imagenesDtoGuardadas.add(mapper.imagenAImagenDTO(imagenBuscada.get()));
                    }
                    else {
                        ImagenDTO imagenNueva = new ImagenDTO(imagenDTO.getTitulo(), imagenDTO.getImage());
                        imagenesDtoGuardadas.add(imagenNueva);
                    }
                }
            }
            productoDTO.setPoliticas(politicasGuardadas);
            productoDTO.setCaracteristicas(caracteristicasGuardadas);
            productoDTO.setImagenes(imagenesDtoGuardadas);
//            productoDTO.setImagenes(imagenesDtoGuardadas);
            Producto productoPorAgregar = mapper.productoDTOAProducto(productoDTO);
            if (imagenesDtoGuardadas.size() > 0) {
//            Producto productoAgregado = productoRepository.save(productoPorAgregar);
//            productoAgregado.setImagenes(imagenesDtoGuardadas.stream().map(this::imagenDTOAImagen).collect(Collectors.toSet()));
                productoPorAgregar.asignarProducto();
            }
            System.out.println("AGREGANDO");
            System.out.println(productoPorAgregar);
            return mapper.productoAProductoDTO(productoRepository.save(productoPorAgregar));
        }
        else {
            throw new EntityNotFoundException("No existe un producto con id: "+productoDTO.getIdProducto());
        }
    }

    public String agregarCaracteristicaProducto(Long id, Caracteristica caracteristica) {
        Optional<Producto> productoBuscado = productoRepository.findById(id);
        if (productoBuscado.isPresent()) {
            Producto productoEncontrado = productoBuscado.get();
            Optional<Caracteristica> caracteristicaBuscada = caracteristicaRepository.buscarCaracteristicaCompletaNombre(caracteristica.getNombre());
            if (caracteristicaBuscada.isPresent()) {
                productoEncontrado.agregarCaracteristicaEnProducto(caracteristicaBuscada.get());
                productoRepository.save(productoEncontrado);
                return "La caracteristica existe y se agrego correctamente al producto";
            } else {
                Caracteristica caracteristicaNueva = new Caracteristica(caracteristica.getNombre(), caracteristica.getIcono());
                productoEncontrado.agregarCaracteristicaEnProducto(caracteristicaNueva);
                productoRepository.save(productoEncontrado);
                return "La caracteristica no existia pero se creo y agrego al producto correctamente";
            }
        } else {
            throw new EntityNotFoundException("No existe el producto con id: "+id);
        }
    }


    public void eliminarProducto(Long id) throws ResourceNotFoundException {
        productoRepository.deleteById(id);
    }

    public List<Producto> listar8ProductosRandom() {
        List<Producto> productos = productoRepository.getRandom8();
        return productos;
    }

    public Optional<Producto> buscarProductoXID(Long id) {
        return productoRepository.findById(id);
    }


//    public ProductoDTO productoAProductoDTO (Producto producto) {
//        ProductoDTO productoDTORespuesta = new ProductoDTO();
//        productoDTORespuesta.setIdProducto(producto.getIdProducto());
//        productoDTORespuesta.setTitulo(producto.getTitulo());
//        productoDTORespuesta.setDescripcion(producto.getDescripcion());
//        productoDTORespuesta.setCategoria(producto.getCategoria().getIdCategoria());
//        productoDTORespuesta.setCiudad(producto.getCiudad().getIdCiudad());
//        productoDTORespuesta.setCaracteristicas(producto.getCaracteristicas());
//        productoDTORespuesta.setPoliticas(producto.getPoliticas());
//        productoDTORespuesta.setPrecio(producto.getPrecio());
//        productoDTORespuesta.setValoracionDeSeguridad(producto.getValoracionDeSeguridad());
//        System.out.println(producto.getImagenes());
//        Set<ImagenDTO> imagenesDto = producto.getImagenes().stream().map(this::imagenAImagenDTO).collect(Collectors.toSet());
//        System.out.println(imagenesDto);
//        productoDTORespuesta.setImagenes(imagenesDto);
//
//        return productoDTORespuesta;
//    }
//
//    public Producto productoDTOAProducto (ProductoDTO productoDTO) {;
////        Producto productoDTOACambiar = mapper.convertValue(productoDTO, Producto.class);
////        System.out.println(productoDTOACambiar);
//        Producto producto = new Producto();
//        Ciudad ciudad = new Ciudad();
//        Categoria categoria = new Categoria();
//        producto.setIdProducto(productoDTO.getIdProducto());
//        ciudad.setIdCiudad(productoDTO.getCiudad());
//        categoria.setIdCategoria(productoDTO.getCategoria());
//        producto.setTitulo(productoDTO.getTitulo());
//        producto.setDescripcion(productoDTO.getDescripcion());
//        producto.setPrecio(productoDTO.getPrecio());
//        producto.setCategoria(categoria);
//        producto.setCiudad(ciudad);
//        producto.setValoracionDeSeguridad(productoDTO.getValoracionDeSeguridad());
//        Set<Caracteristica> caracteristicas = productoDTO.getCaracteristicas();
//        Set<Caracteristica> caracteristicasPorGuardar = new HashSet<>();
//        if (caracteristicas != null) {
//            for (Caracteristica caracteristica: caracteristicas) {
//                caracteristicasPorGuardar.add(caracteristica);
//            }
//        }
//        producto.setCaracteristicas(caracteristicasPorGuardar);
//        Set<Politica> politicas = productoDTO.getPoliticas();
//        Set<Politica> politicasPorGuardar = new HashSet<>();
//        if (politicas != null) {
//            for (Politica politica: politicas) {
//                politicasPorGuardar.add(politica);
//            }
//        }
//        producto.setPoliticas(politicasPorGuardar);
//        Set<ImagenDTO> imagenesDTO = productoDTO.getImagenes();
//        Set<Imagen> imagenes = new HashSet<>();
//        if (imagenesDTO != null) {
//            imagenes = imagenesDTO.stream().map(imagenDTO -> imagenDTOAImagen(imagenDTO)).collect(Collectors.toSet());
//        }
//        System.out.println(imagenes);
//        producto.setImagenes(imagenes);
//
//        return producto;
//    }

//    public Imagen imagenDTOAImagen (ImagenDTO imagenDTO) {
//        Imagen imagen = new Imagen();
//        imagen.setId(imagenDTO.getId());
//        imagen.setTitulo(imagenDTO.getTitulo());
//        imagen.setImage(imagenDTO.getImage());
//
//        return imagen;
//    }
//
//    public ImagenDTO imagenAImagenDTO (Imagen imagen) {
//        ImagenDTO imagenDto = new ImagenDTO();
//        imagenDto.setId(imagen.getId());
//        imagenDto.setTitulo(imagen.getTitulo());
//        imagenDto.setImage(imagen.getImage());
//        imagenDto.setIdProducto(imagen.getProducto().getIdProducto());
//        return imagenDto;
//    }
}