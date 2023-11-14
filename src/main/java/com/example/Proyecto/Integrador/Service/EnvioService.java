package com.example.Proyecto.Integrador.Service;

import com.example.Proyecto.Integrador.Dto.EnvioDto;
import com.example.Proyecto.Integrador.Dto.EnvioDtoRequest;
import com.example.Proyecto.Integrador.Dto.EnvioDtoUpdate;
import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.*;
import com.example.Proyecto.Integrador.Repository.ClienteRepository;
import com.example.Proyecto.Integrador.Repository.EmpleadoRepository;
import com.example.Proyecto.Integrador.Repository.EnvioRepository;
import com.example.Proyecto.Integrador.Repository.PaqueteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {
    private final EnvioRepository envioRepository;
    private final ClienteRepository clienteRepository;
    private final PaqueteRepository paqueteRepository;
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EnvioService(EnvioRepository envioRepository, ClienteRepository clienteRepository, PaqueteRepository paqueteRepository, EmpleadoRepository empleadoRepository) {
        this.envioRepository = envioRepository;
        this.clienteRepository = clienteRepository;
        this.paqueteRepository = paqueteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public EnvioDtoRequest crearEnvio(EnvioDto envioDto) {
        if (envioDto.getCedula() == null ||
                envioDto.getNombreRemitente() == null ||
                envioDto.getCiudadOrigen() == null ||
                envioDto.getCiudadDestino() == null ||
                envioDto.getDireccionDestino() == null ||
                envioDto.getNombrePersona() == null ||
                envioDto.getNumeroPersona() == null ||
                envioDto.getPeso() == null ||
                envioDto.getValorDeclarado() == null) {
            throw new ApiRequestException("Algunos de los campos ingresados estan vacios");
        }
        Optional<Cliente> clienteOptional = this.clienteRepository.findById(envioDto.getCedula());
        if (clienteOptional.isPresent()) {
            Paquete paquete = new Paquete(Paquete.asignarTipoPaquete(envioDto.getPeso()),envioDto.getPeso(),envioDto.getValorDeclarado());
            this.paqueteRepository.save(paquete);
            Envio envio = new Envio(
                    clienteOptional.get(),
                    envioDto.getCiudadOrigen(),
                    envioDto.getCiudadDestino(),
                    envioDto.getDireccionDestino(),
                    envioDto.getNombrePersona(),
                    envioDto.getNumeroPersona(),
                    asignarHora(),
                    EstadoEnvioEnum.RECIBIDO,
                    Envio.asignarPrecioEnvio(paquete.getTipoPaquete()),
                    paquete
            );
            Envio envio1 = this.envioRepository.save(envio);
            envio1.setNumGuia(envio1.getNumGuia());
            return new EnvioDtoRequest(envio1.getNumGuia(),envio1.getEstadoEnvio());
        } else {
            throw new ApiRequestException("El cliente con cedula " + envioDto.getCedula() + " debe de estar registrado para poder enviar el paquete.");
        }
    }

    public String asignarHora() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public EnvioDto obtenerNumeroGuia(Integer numGuia) {
        Optional<Envio> envio = this.envioRepository.findById(numGuia);
        if (!envio.isPresent()) {
            throw new ApiRequestException("El numero de guia no existe");
        }
        Integer cedula = envio.get().getCliente().getCedula();
        String nombre = envio.get().getCliente().getNombre();
        String ciudadOrigen = envio.get().getCiudadOrigen();
        String ciudadDestino = envio.get().getCiudadDestino();
        String direccionDestino = envio.get().getDireccionDestino();
        String nombrePersona = envio.get().getNombrePersona();
        Integer celular = envio.get().getNumeroPersona();
        Integer valorDeclarado = envio.get().getPaquete().getValorDeclarado();
        Double peso = envio.get().getPaquete().getPeso();
        Integer valorEnvio = envio.get().getValorEnvio();
        EstadoEnvioEnum estadoEnvio = envio.get().getEstadoEnvio();
        EnvioDto envioDto = new EnvioDto(
                cedula, nombre,ciudadOrigen,ciudadDestino,direccionDestino,nombrePersona,celular,peso,estadoEnvio,valorDeclarado);
        if (estadoEnvio.equals(EstadoEnvioEnum.RECIBIDO)) {
            envio.get().setEstadoEnvio(EstadoEnvioEnum.RUTA);
            envioDto.setEstadoEnvio(EstadoEnvioEnum.RUTA);
            this.envioRepository.save(envio.get());
        } else {
            envioDto.setEstadoEnvio(estadoEnvio);
        }
        envioDto.setValorEnvio(valorEnvio);
        return envioDto;
    }


    public EnvioDtoRequest actualizarEstadoPaquete(EnvioDtoUpdate envioDtoUpdate) {
        Integer cedula = envioDtoUpdate.getCedula();
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedula);
        if (!empleado.isPresent()) {
            throw new ApiRequestException("El empleado con cedula " + cedula + " no existe en nuestra compania");
        }
        Integer numGuia = envioDtoUpdate.getNumGuia();
        Optional<Envio> envioOptional = this.envioRepository.findById(numGuia);
        if (!envioOptional.isPresent()) {
            throw new ApiRequestException("El numero de guia no existe");
        }
        TipoEmpleadoEnum tipoEmpleado = empleado.get().getTipoEmpleado();
        EstadoEnvioEnum estadoEnvio = envioDtoUpdate.getEstadoEnvio();
        EstadoEnvioEnum estadoEnvioActual = envioOptional.get().getEstadoEnvio();
        if (tipoEmpleado.equals(TipoEmpleadoEnum.REPARTIDOR) || tipoEmpleado.equals(TipoEmpleadoEnum.COORDINADOR)) {
            if ((estadoEnvioActual.equals(EstadoEnvioEnum.RECIBIDO) && estadoEnvio.equals(EstadoEnvioEnum.RUTA)) ||
                    (estadoEnvioActual.equals(EstadoEnvioEnum.RUTA) && estadoEnvio.equals(EstadoEnvioEnum.ENTREGADO))) {
                envioOptional.get().setEstadoEnvio(estadoEnvio);
                this.envioRepository.save(envioOptional.get());
            } else {
                throw new ApiRequestException((estadoEnvioActual.equals(EstadoEnvioEnum.RECIBIDO) && estadoEnvio.equals(EstadoEnvioEnum.ENTREGADO)) ?
                        "el cambio de estado no cumple con las validaciones" :
                        "El tipo de estado no existe en la base de datos" + estadoEnvio);
            }
        } else {
            throw new ApiRequestException("El tipo de empleado no tiene permiso para actualizar el estado del envío");
        }
        EnvioDtoRequest envioDtoRequest = new EnvioDtoRequest();
        envioDtoRequest.setNumGuia(envioOptional.get().getNumGuia());
        envioDtoRequest.setEstadoEnvio(envioOptional.get().getEstadoEnvio());
        return envioDtoRequest;
    }

    public List<Envio> filtrarPorEstado(EstadoEnvioEnum estadoEnvio, Integer cedula) {
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedula);
        if (!empleado.isPresent()) {
            throw new ApiRequestException("La cedula del empleado no existe");
        }
        return this.envioRepository.envioPorEstado(estadoEnvio);
    }
}





