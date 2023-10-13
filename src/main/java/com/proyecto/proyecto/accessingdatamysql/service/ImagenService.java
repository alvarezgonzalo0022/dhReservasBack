package com.proyecto.proyecto.accessingdatamysql.service;

import com.proyecto.proyecto.accessingdatamysql.dto.ImagenDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import com.proyecto.proyecto.accessingdatamysql.model.Mapper;
import com.proyecto.proyecto.accessingdatamysql.model.Producto;
import com.proyecto.proyecto.accessingdatamysql.model.Validator;
import com.proyecto.proyecto.accessingdatamysql.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImagenService {
    private ImagenRepository imagenRepository;

    private Mapper mapper;

    private Validator validator;

    private ProductoService productoService;

    @Autowired
    public ImagenService(ImagenRepository imagenRepository, ProductoService productoService, Mapper mapper, Validator validator) {
        this.imagenRepository = imagenRepository;
        this.productoService = productoService;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<ImagenDTO> listarImagenes() {
        return imagenRepository.findAll().stream().map(imagen -> mapper.imagenAImagenDTO(imagen)).collect(Collectors.toList());
    }

    public Optional<Imagen> buscarImagenXID(Long id) {
        return imagenRepository.findById(id);
    }

    public ImagenDTO guardarImagen(ImagenDTO imagenDTO) {

        imagenDTO = validator.validarImagenDTO(imagenDTO);

        Optional<Imagen> imagenBuscada = imagenRepository.buscarImagenUrl(imagenDTO.getImage());
        if (imagenBuscada.isPresent()) {
            return mapper.imagenAImagenDTO(imagenRepository.save(imagenBuscada.get()));
        }
        else {
            return mapper.imagenAImagenDTO(imagenRepository.save(mapper.imagenDTOAImagen(imagenDTO)));
        }
    }

    public ImagenDTO actualizarImagen(ImagenDTO imagenDTO) throws ResourceNotFoundException {

        imagenDTO = validator.validarImagenDTO(imagenDTO);

        Optional<Imagen> imagenBuscada = buscarImagenXID(imagenDTO.getId());
        if (imagenBuscada.isPresent()){
            return mapper.imagenAImagenDTO(imagenRepository.save(mapper.imagenDTOAImagen(imagenDTO)));
        }
        else{
            throw new EntityNotFoundException("No existe la imagen con id: "+imagenDTO.getId());
        }
    }


    public void eliminarImagen(Long id) {
        System.out.println(id);
        imagenRepository.deleteById(id);
    }

//    public Imagen imagenDTOAImagen (ImagenDTO imagenDTO) {
//        Imagen imagen = new Imagen();
//        Producto producto = new Producto();
//        producto.setIdProducto(imagenDTO.getIdProducto());
//        imagen.setTitulo(imagenDTO.getTitulo());
//        imagen.setImage(imagenDTO.getImage());
//        imagen.setProducto(producto);
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
//
//        return imagenDto;
//    }

}
