package com.proyecto.proyecto.accessingdatamysql.controller;

import com.proyecto.proyecto.accessingdatamysql.dto.ReservaDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.CustomConstraintViolationException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.ApiValidationError;
import com.proyecto.proyecto.accessingdatamysql.model.Mapper;
import com.proyecto.proyecto.accessingdatamysql.model.Reserva;
import com.proyecto.proyecto.accessingdatamysql.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reservas")
public class ReservaController {

    private ReservaService reservaService;

    private Mapper mapper;

    @Autowired
    public ReservaController(ReservaService reservaService, Mapper mapper) {
        this.reservaService = reservaService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> buscarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/listarReservasPorIdProducto/{idProducto}")
    public ResponseEntity<List<ReservaDTO>> buscarReservasPorIdProducto(@PathVariable Long idProducto) {
        Set<ApiValidationError> validationErrors = new HashSet<>();
        if (idProducto == null) {
            validationErrors.add(new ApiValidationError("id", "El número de ID de Producto no puede ser nulo"));
            throw new CustomConstraintViolationException("", validationErrors);
        }
        return ResponseEntity.ok(reservaService.listarReservasPorIdProducto(idProducto));
    }

    @GetMapping("/listarReservasPorIdUsuario/{idUsuario}")
    public ResponseEntity<List<ReservaDTO>> buscarReservasPorIdUsuario(@PathVariable Long idUsuario) {
        Set<ApiValidationError> validationErrors = new HashSet<>();
        if (idUsuario == null) {
            validationErrors.add(new ApiValidationError("id", "El número de ID de Usuario no puede ser nulo"));
            throw new CustomConstraintViolationException("", validationErrors);
        }
        return ResponseEntity.ok(reservaService.listarReservasPorIdUsuario(idUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarReserva(@PathVariable Long id) {
        Set<ApiValidationError> validationErrors = new HashSet<>();
        if (id == null) {
            validationErrors.add(new ApiValidationError("id", "El número de ID de Reserva no puede ser nulo"));
            throw new CustomConstraintViolationException("", validationErrors);
        }
        Optional<Reserva> reservaBuscada = reservaService.buscarReservaXID(id);
        if (reservaBuscada.isPresent()){
            return ResponseEntity.ok(mapper.reservaAReservaDTO(reservaBuscada.get()));
        }
        else{
            throw new EntityNotFoundException("No existe la reserva con id: "+id);
        }
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> guardarReserva(@RequestBody ReservaDTO reservaDto) throws BadRequestException, ResourceNotFoundException {
        System.out.println("RESERVADTO");
        System.out.println(reservaDto.getFechaInicialReserva());
        System.out.println(reservaDto.getFechaFinalReserva());
        return ResponseEntity.ok(reservaService.guardarReserva(reservaDto));
    }

    @PutMapping
    public ResponseEntity<ReservaDTO> actualizarReserva(@RequestBody ReservaDTO reservaDto) throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(reservaService.actualizarReserva(reservaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarReserva(@PathVariable Long id) throws ResourceNotFoundException {
        Set<ApiValidationError> validationErrors = new HashSet<>();
        if (id == null) {
            validationErrors.add(new ApiValidationError("id", "El número de ID de Reserva no puede ser nulo"));
            throw new CustomConstraintViolationException("", validationErrors);
        }
        Optional<Reserva> reservaBuscada = reservaService.buscarReservaXID(id);
        if (reservaBuscada.isPresent()){
            reservaService.eliminarReserva(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino la reserva con id: "+id);
        }
        else{
            throw new EntityNotFoundException("No existe una reserva con id: "+id);
        }
    }
}
