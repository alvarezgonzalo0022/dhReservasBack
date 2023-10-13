package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.dto.ImagenDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.Imagen;
import com.proyecto.proyecto.accessingdatamysql.model.Mapper;
import com.proyecto.proyecto.accessingdatamysql.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/imagenes")
public class ImagenController {

    private ImagenService imagenService;
    private Mapper mapper;

    @Autowired
    public ImagenController(ImagenService imagenService, Mapper mapper) {
        this.imagenService = imagenService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ImagenDTO>> buscarImagenes() {
        return ResponseEntity.ok(imagenService.listarImagenes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> buscarImagen(@PathVariable Long id) {
        Optional<Imagen> imagenBuscada = imagenService.buscarImagenXID(id);
        if (imagenBuscada.isPresent()){
            return ResponseEntity.ok(mapper.imagenAImagenDTO(imagenBuscada.get()));
        }
        else{
            throw new  EntityNotFoundException("No existe una imagen con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<ImagenDTO> guardarImagen(@RequestBody ImagenDTO imagenDTO) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(imagenService.guardarImagen(imagenDTO));
    }

    @PutMapping
    public ResponseEntity<ImagenDTO> actualizarImagen(@RequestBody ImagenDTO imagenDTO) throws ResourceNotFoundException {
//        Optional<Imagen> imagenBuscada = imagenService.buscarImagenXID(imagenDTO.getId());
//        if (imagenBuscada.isPresent()){
//            imagenService.actualizarImagen(imagenDTO, imagenBuscada.get());
//            return ResponseEntity.ok("Se actulizo la imagen con id: "+imagenDTO.getId());
//        }
//        else{
//            return ResponseEntity.badRequest().body("No existe la imagen con id: "+imagenDTO.getId());
//        }
        return ResponseEntity.ok(imagenService.actualizarImagen(imagenDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarImagen(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Imagen> imagenBuscada = imagenService.buscarImagenXID(id);
        if (imagenBuscada.isPresent()){
            imagenService.eliminarImagen(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la imagen con id: "+id);
        }
        else{
            throw new EntityNotFoundException("No existe una imagen con id: "+id);
        }
    }
}
