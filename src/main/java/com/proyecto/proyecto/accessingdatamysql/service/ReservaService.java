package com.proyecto.proyecto.accessingdatamysql.service;

import com.proyecto.proyecto.accessingdatamysql.dto.ReservaDTO;
import com.proyecto.proyecto.accessingdatamysql.exceptions.BadRequestException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.CustomConstraintViolationException;
import com.proyecto.proyecto.accessingdatamysql.exceptions.ResourceNotFoundException;
import com.proyecto.proyecto.accessingdatamysql.model.*;
import com.proyecto.proyecto.accessingdatamysql.model.Cliente;
import com.proyecto.proyecto.accessingdatamysql.repository.ClienteRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.ProductoRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.ReservaRepository;
import com.proyecto.proyecto.accessingdatamysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    private ReservaRepository reservaRepository;
    private ProductoService productoService;

    private Mapper mapper;

    private Validator validator;

    private ProductoRepository productoRepository;

    private ClienteRepository clienteRepository;
    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ProductoService productoService, ClienteRepository clienteRepository, ProductoRepository productoRepository, Mapper mapper, Validator validator) {
        this.reservaRepository = reservaRepository;
        this.productoService = productoService;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<ReservaDTO> listarReservas() {
        return reservaRepository.findAll().stream().map(mapper::reservaAReservaDTO).collect(Collectors.toList());
    }

    public List<ReservaDTO> listarReservasPorIdProducto(Long idProducto) {
        return reservaRepository.buscarReservasPorIdProducto(idProducto).stream().map(mapper::reservaAReservaDTO).collect(Collectors.toList());
    }

    public List<ReservaDTO> listarReservasPorIdUsuario(Long idUsuario) {
        return reservaRepository.buscarReservasPorIdUsuario(idUsuario).stream().map(mapper::reservaAReservaDTO).collect(Collectors.toList());
    }

    public Optional<Reserva> buscarReservaXID(Long id) {
        return reservaRepository.findById(id);
    }

    public ReservaDTO guardarReserva(ReservaDTO reserva) throws ResourceNotFoundException {

        reserva = validator.validarReservaDTO(reserva);

//      Verificar si existe una reserva vinculada al producto y cliente en las mismas fechas
        Optional<Reserva> reservaBuscada = reservaRepository.buscarReservaProductoClienteFechas(reserva.getProducto(), reserva.getCliente(), reserva.getFechaInicialReserva(), reserva.getFechaFinalReserva());
        if (reservaBuscada.isPresent()) {
//          Si existe, guardar la reserva ya existente, aunque podemos cambiar a que lance un error "La reserva ya existe"
            return mapper.reservaAReservaDTO(reservaRepository.save(reservaBuscada.get()));
        }
        else {
//          No existe, ahora podemos guardar la reserva ingresada
//          Verificamos que exista el cliente ingresado
            Optional<Cliente> clienteBuscado = clienteRepository.findById(reserva.getCliente());
            if (!clienteBuscado.isPresent()) {
//                El cliente no existe, no podemos guardar la reserva
                throw new EntityNotFoundException("No existe un cliente con id: "+reserva.getCliente());
            }
//          Verificamos que exista el producto ingresado
            Optional<Producto> productoBuscado = productoRepository.findById(reserva.getProducto());
            if (!productoBuscado.isPresent()) {
//                El producto no existe, no podemos guardar la reserva
                throw new EntityNotFoundException("No existe un producto con id: "+reserva.getProducto());
            }
//          Ahora tenemos que verificar que no exista ninguna reserva que coincida con las fechas ingresadas en la reserva a crear
//            List<Reserva> reservasBuscadas = reservaRepository.buscarReservasPorRangoDeFechas(reserva.getFechaInicialReserva(), reserva.getFechaFinalReserva());
////            if (!reservasBuscadas.isEmpty()) {
//////                La lista no está vacía, no podemos crear la reserva porque habría conflicto en las fechas de las reservas
////                throw new EntityExistsException("Ya existe una o más reservas en las fechas seleccionadas");
////            }
//            En caso de no haber ninguna reserva en las fechas seleccionadas, vamos a guardar la reserva
            reserva.setIdReserva(null);
            return mapper.reservaAReservaDTO(reservaRepository.save(mapper.reservaDTOAReserva(reserva)));
        }
    }

    public ReservaDTO actualizarReserva(ReservaDTO reserva) throws ResourceNotFoundException, BadRequestException {

        reserva = validator.validarReservaDTO(reserva);

//        Verificar que exista la reserva que vamos a actualizar
        Optional<Reserva> reservaBuscada = reservaRepository.findById(reserva.getIdReserva());
        if (reservaBuscada.isPresent()) {
            //La reserva existe, ahora tenemos que verificar que no exista otra reserva con el mismo producto y cliente en las mismas fechas
            // O en caso de que exista, que el id sea el mismo
            Optional<Reserva> reservaProductoClienteFechas = reservaRepository.buscarReservaProductoClienteFechas(reserva.getProducto(), reserva.getCliente(), reserva.getFechaInicialReserva(), reserva.getFechaFinalReserva());
            if (reservaProductoClienteFechas.isPresent()) {
                //Existe una reserva con el mismo producto, cliente y fechas
                if (reservaProductoClienteFechas.get().getIdReserva() == reserva.getIdReserva()) {
                    //El id es el mismo, así que podemos actualizar la reserva
                    return mapper.reservaAReservaDTO(reservaRepository.save(mapper.reservaDTOAReserva(reserva)));
                } else  {
                    //El id no es el mismo, no podemos guardar una reserva si ya existe otra con el mismo producto, cliente y fechas
                    throw new EntityExistsException("Ya existe otra reserva con ese producto y cliente en las mismas fechas");
                }
            } else {
                //No existe una reserva con el mismo producto, cliente y fechas, eso quiere decir que estamos actualizando al menos el cliente, el producto o las fechas de las reservas
                //Hay que verificar que exista el cliente, el producto y que no hay reservas en las mismas fechas

                //Verificamos que exista el cliente
                Optional<Cliente> clienteBuscado = clienteRepository.findById(reserva.getCliente());
                if (!clienteBuscado.isPresent()) {
                    //El cliente no existe
                    throw new EntityNotFoundException("No existe el cliente con id: "+reserva.getCliente());
                }
                //Verificamos que exista el producto
                Optional<Producto> productoBuscado = productoRepository.findById(reserva.getProducto());
                if (!productoBuscado.isPresent()) {
                    //El producto no existe
                    throw new EntityNotFoundException("No existe el producto con id: "+reserva.getProducto());
                }
                //Verificamos que no exista otra reserva en las mismas fechas que la reserva ingresada
                List<Reserva> listaReservasBuscadas = reservaRepository.buscarReservasPorRangoDeFechas(reserva.getFechaInicialReserva(), reserva.getFechaFinalReserva());
                System.out.println(reserva.getFechaInicialReserva());
                if (!listaReservasBuscadas.isEmpty()) {
                    throw new EntityExistsException("Ya existe una o más reservas dentro de las fechas ingresadas");
                }
                return mapper.reservaAReservaDTO(reservaRepository.save(mapper.reservaDTOAReserva(reserva)));
            }
        } else {
            throw new EntityNotFoundException("No existe una reserva con id: "+reserva.getIdReserva());
        }

    }


    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

//    public ReservaDTO reservaAReservaDTO(Reserva reserva) {
//        ReservaDTO reservaDto = new ReservaDTO();
//        reservaDto.setIdReserva(reserva.getIdReserva());
//        reservaDto.setHoraComienzoReserva(reserva.getHoraComienzoReserva());
//        reservaDto.setFechaInicialReserva(reserva.getFechaInicialReserva());
//        reservaDto.setFechaFinalReserva(reserva.getFechaFinalReserva());
//        reservaDto.setProducto(reserva.getProducto().getIdProducto());
//        reservaDto.setCliente(reserva.getUsuario().getIdUsuario());
//        return reservaDto;
//    }

//    public Reserva reservaDTOAReserva(ReservaDTO reservaDTO) {
//        Reserva reserva = new Reserva();
//        Producto producto = new Producto();
//        producto.setIdProducto(reservaDTO.getProducto());
//        Cliente cliente = new Cliente();
//        cliente.setIdUsuario(reservaDTO.getCliente());
//        reserva.setIdReserva(reservaDTO.getIdReserva());
//        reserva.setHoraComienzoReserva(reservaDTO.getHoraComienzoReserva());
//        reserva.setFechaInicialReserva(reservaDTO.getFechaInicialReserva());
//        reserva.setFechaFinalReserva(reservaDTO.getFechaFinalReserva());
//        reserva.setUsuario(cliente);
//        reserva.setProducto(producto);
//        return reserva;
//    }

}
