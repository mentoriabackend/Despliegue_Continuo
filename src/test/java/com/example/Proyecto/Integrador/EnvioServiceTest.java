package com.example.Proyecto.Integrador;

import com.example.Proyecto.Integrador.Dto.EnvioDto;
import com.example.Proyecto.Integrador.Dto.EnvioDtoRequest;
import com.example.Proyecto.Integrador.Dto.EnvioDtoUpdate;
import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.*;
import com.example.Proyecto.Integrador.Repository.ClienteRepository;
import com.example.Proyecto.Integrador.Repository.EmpleadoRepository;
import com.example.Proyecto.Integrador.Repository.EnvioRepository;
import com.example.Proyecto.Integrador.Repository.PaqueteRepository;
import com.example.Proyecto.Integrador.Service.EnvioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class EnvioServiceTest {
    EnvioRepository envioRepository;
    EmpleadoRepository empleadoRepository;
    ClienteRepository clienteRepository;
    PaqueteRepository paqueteRepository;
    EnvioService envioService;


    @BeforeEach
    void SetUp() {
        this.clienteRepository = mock(ClienteRepository.class);
        this.empleadoRepository = mock(EmpleadoRepository.class);
        this.envioRepository = mock(EnvioRepository.class);
        this.paqueteRepository = mock(PaqueteRepository.class);
        this.envioService = new EnvioService(envioRepository, clienteRepository, paqueteRepository, empleadoRepository);
    }

    @Test
    void crearEnvio() {
        // Arrange
        EnvioDto envioDto = new EnvioDto();
        envioDto.setCedula(123456789);
        envioDto.setNombreRemitente("Juan Perez");
        envioDto.setCiudadOrigen("Bogota");
        envioDto.setCiudadDestino("Medellin");
        envioDto.setDireccionDestino("Calle 1 # 2-3");
        envioDto.setNombrePersona("Maria");
        envioDto.setNumeroPersona(321654987);
        envioDto.setPeso(2.5);
        envioDto.setValorDeclarado(50000);

        Cliente cliente = new Cliente();
        cliente.setCedula(123456789);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        Paquete paquete = new Paquete();
        paquete.setTipoPaquete(TipoPaqueteEnum.LIVIANO);
        when(paqueteRepository.save(any(Paquete.class))).thenReturn(paquete);

        Envio envio = new Envio();
        envio.setNumGuia(1);
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        // Act
        EnvioDtoRequest result = envioService.crearEnvio(envioDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(envio.getNumGuia(), result.getNumGuia());
    }

    @Test
    void crearEnvioConDatosNull() {
        EnvioDto envioDto = new EnvioDto(null, null, null, null, null, null, null, null, null,null);
        when(envioRepository.findById(envioDto.getCedula())).thenReturn(Optional.empty());
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.crearEnvio(envioDto),
                "Algunos de los campos ingresados estan vacios"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("Algunos de los campos ingresados estan vacios"));
    }
    @Test
    void crearEnvioSinCliente(){
        EnvioDto envioDto = new EnvioDto();
        envioDto.setCedula(123456789);
        envioDto.setNombreRemitente("Juan Perez");
        envioDto.setCiudadOrigen("Bogota");
        envioDto.setCiudadDestino("Medellin");
        envioDto.setDireccionDestino("Calle 1 # 2-3");
        envioDto.setNombrePersona("Maria");
        envioDto.setNumeroPersona(321654987);
        envioDto.setPeso(2.5);
        envioDto.setValorDeclarado(50000);

        Cliente cliente = new Cliente();
        cliente.setCedula(123456789);
        when(clienteRepository.findById(9999)).thenReturn(Optional.of(cliente));

        Paquete paquete = new Paquete();
        paquete.setTipoPaquete(TipoPaqueteEnum.LIVIANO);
        when(paqueteRepository.save(any(Paquete.class))).thenReturn(paquete);

        Envio envio = new Envio();
        envio.setNumGuia(1);
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);
        when(envioRepository.findById(envioDto.getCedula())).thenReturn(Optional.empty());
        ApiRequestException thrown = assertThrows(
            ApiRequestException.class,
            () -> this.envioService.crearEnvio(envioDto),
                "El cliente con cedula " + envioDto.getCedula() + " debe de estar registrado para poder enviar el paquete."
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("El cliente con cedula " + envioDto.getCedula() + " debe de estar registrado para poder enviar el paquete."
        ));
        }
    @Test
    void obtenerSinNumeroGuia() {
        Integer numGuia = null;
        Envio envio = new Envio();
        envio.setNumGuia(numGuia);
        when(envioRepository.findById(numGuia)).thenReturn(Optional.empty());
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.obtenerNumeroGuia(numGuia),
                "El numero de guia no existe"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("El numero de guia no existe"));
    }

    @Test
    void obtenerNumeroGuia() {
        // Arrange
        Integer numGuia = 123;
        Cliente cliente = new Cliente();
        Paquete paquete = new Paquete();
        Envio envio = new Envio(cliente, "Bogotá", "Medellín",
                "Cra 40 # 123", "Maria", 1234567890, "10:10", EstadoEnvioEnum.RECIBIDO, 50000, paquete);
        envio.setNumGuia(numGuia);
        when(envioRepository.findById(numGuia)).thenReturn(Optional.of(envio));

        // Act
        EnvioDto envioDto = envioService.obtenerNumeroGuia(envio.getNumGuia());

        // Assert
        Assertions.assertNotNull(envioDto);
        Assertions.assertEquals(numGuia, envio.getNumGuia());
        Assertions.assertEquals(cliente.getCedula(), envioDto.getCedula());
        Assertions.assertEquals(cliente.getNombre(), envioDto.getNombreRemitente());
        Assertions.assertEquals(envio.getCiudadOrigen(), envioDto.getCiudadOrigen());
        Assertions.assertEquals(envio.getCiudadDestino(), envioDto.getCiudadDestino());
        Assertions.assertEquals(envio.getDireccionDestino(), envioDto.getDireccionDestino());
        Assertions.assertEquals(envio.getNombrePersona(), envioDto.getNombrePersona());
        Assertions.assertEquals(envio.getNumeroPersona(), envioDto.getNumeroPersona());
        Assertions.assertEquals(paquete.getValorDeclarado(), envioDto.getValorDeclarado());
        Assertions.assertEquals(paquete.getPeso(), envioDto.getPeso());
        Assertions.assertEquals(envio.getValorEnvio(), envioDto.getValorEnvio());
        Assertions.assertEquals("EN RUTA", envioDto.getEstadoEnvio());
        verify(envioRepository, times(1)).findById(numGuia);
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void actualizarEstadoPaquetevalido() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setCedula(123);
        empleado.setTipoEmpleado(TipoEmpleadoEnum.REPARTIDOR);
        Cliente cliente = new Cliente();
        cliente.setCedula(1);
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(12);
        Envio envio = new Envio(cliente,
                "Bogotá", "Medellín",
                "Cra 40 # 123",
                "Maria", 1234567890,
                "10:10",
                EstadoEnvioEnum.RECIBIDO,
                50000,
                paquete);
        EnvioDtoUpdate envioDtoUpdate = new EnvioDtoUpdate();
        envioDtoUpdate.setNumGuia(envio.getNumGuia());
        envioDtoUpdate.setCedula(empleado.getCedula());
        envioDtoUpdate.setEstadoEnvio(EstadoEnvioEnum.RUTA);

        when(empleadoRepository.findById(empleado.getCedula())).thenReturn(Optional.of(empleado));
        when(envioRepository.findById(envio.getNumGuia())).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);

        // Act
        EnvioDtoRequest result = envioService.actualizarEstadoPaquete(envioDtoUpdate);

        //Assert
        verify(empleadoRepository, times(1)).findById(empleado.getCedula());
        verify(envioRepository, times(1)).findById(envio.getNumGuia());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void actualizarEstadoPaqueteSinEmpleado() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setCedula(123);
        empleado.setTipoEmpleado(TipoEmpleadoEnum.COORDINADOR);
        Cliente cliente = new Cliente();
        cliente.setCedula(1);
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(12);
        Envio envio = new Envio(cliente, "Bogotá", "Medellín",
                "Cra 40 # 123", "Maria", 1234567890, "10:10", EstadoEnvioEnum.RECIBIDO, 50000, paquete);
        EnvioDto envioDto = new EnvioDto();
        EnvioDtoRequest envioDtoRequest = new EnvioDtoRequest();
        envioDto.setCedula(null);
        envioDto.setEstadoEnvio(EstadoEnvioEnum.ENTREGADO);
        envioDtoRequest.setNumGuia(1234);
        EnvioDtoUpdate envioDtoUpdate = new EnvioDtoUpdate();

        when(empleadoRepository.findById(123)).thenReturn(Optional.of(empleado));
        when(envioRepository.findById(envio.getNumGuia())).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.actualizarEstadoPaquete(envioDtoUpdate),
                "El empleado con cedula " + envioDto.getCedula()+ " no existe en nuestra compania"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("El empleado con cedula " + envioDto.getCedula() + " no existe en nuestra compania"));
    }

    @Test
    void actualizarEstadoPaqueteSinNumeroGuia() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setCedula(12345);
        empleado.setTipoEmpleado(TipoEmpleadoEnum.COORDINADOR);
        Cliente cliente = new Cliente();
        cliente.setCedula(1);
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(12);
        Envio envio = new Envio(cliente,
                "Bogotá",
                "Medellín",
                "Cra 40 # 123",
                "Maria",
                1234567890,
                "10:10",
                EstadoEnvioEnum.RECIBIDO,
                50000,
                paquete);
        envio.setNumGuia(12345);
        EnvioDtoUpdate envioDtoUpdate = new EnvioDtoUpdate();
        envioDtoUpdate.setCedula(empleado.getCedula());
        envioDtoUpdate.setNumGuia(123456);
        envioDtoUpdate.setEstadoEnvio(EstadoEnvioEnum.ENTREGADO);

        when(empleadoRepository.findById(empleado.getCedula())).thenReturn(Optional.of(empleado));
        when(envioRepository.findById(9999)).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.actualizarEstadoPaquete(envioDtoUpdate),
                "El numero de guia no existe"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("El numero de guia no existe"));
    }

    @Test
    void actualizarEstadoPaqueteNoCumple() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setCedula(123);
        empleado.setTipoEmpleado(TipoEmpleadoEnum.COORDINADOR);
        Cliente cliente = new Cliente();
        cliente.setCedula(1);
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(12);
        Envio envio = new Envio(cliente,
                "Bogotá",
                "Medellín",
                "Cra 40 # 123",
                "Maria",
                1234567890,
                "10:10",
                EstadoEnvioEnum.RECIBIDO,
                50000,
                paquete);
        EnvioDtoUpdate envioDtoUpdate = new EnvioDtoUpdate();
        envioDtoUpdate.setCedula(empleado.getCedula());
        envioDtoUpdate.setNumGuia(envio.getNumGuia());
        envioDtoUpdate.setEstadoEnvio(EstadoEnvioEnum.ENTREGADO);

        when(empleadoRepository.findById(empleado.getCedula())).thenReturn(Optional.of(empleado));
        when(envioRepository.findById(envio.getNumGuia())).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.actualizarEstadoPaquete(envioDtoUpdate),
                "el cambio de estado no cumple con las validaciones"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("el cambio de estado no cumple con las validaciones"));
    }


    @Test
    void actualizarEstadoPaqueteExitosamente() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setCedula(123);
        empleado.setTipoEmpleado(TipoEmpleadoEnum.COORDINADOR);
        Cliente cliente = new Cliente();
        cliente.setCedula(1);
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(12);
        Envio envio = new Envio(cliente,
                "Bogotá",
                "Medellín",
                "Cra 40 # 123",
                "Maria",
                1234567890,
                "10:10",
                EstadoEnvioEnum.RUTA,
                50000,
                paquete);
        EnvioDtoUpdate envioDtoUpdate = new EnvioDtoUpdate();
        envioDtoUpdate.setCedula(empleado.getCedula());
        envioDtoUpdate.setNumGuia(envio.getNumGuia());
        envioDtoUpdate.setEstadoEnvio(EstadoEnvioEnum.ENTREGADO);

        when(empleadoRepository.findById(empleado.getCedula())).thenReturn(Optional.of(empleado));
        when(envioRepository.findById(envio.getNumGuia())).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);

        // Act
        EnvioDtoRequest result = envioService.actualizarEstadoPaquete(envioDtoUpdate);

        //Assert
        verify(empleadoRepository, times(1)).findById(empleado.getCedula());
        verify(envioRepository, times(1)).findById(envio.getNumGuia());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void filtrarPorEstadoExitosamente() {
        Integer cedula = 123456789;
        EstadoEnvioEnum estadoEnvio = EstadoEnvioEnum.ENTREGADO;

        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        Mockito.when(empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));

        List<Envio> envios = new ArrayList<>();
        Envio envio = new Envio();
        envio.setEstadoEnvio(estadoEnvio);
        envios.add(envio);
        Mockito.when(envioRepository.envioPorEstado(estadoEnvio)).thenReturn(envios);

        List<Envio> result = envioService.filtrarPorEstado(estadoEnvio, cedula);

        verify(empleadoRepository, Mockito.times(1)).findById(cedula);
        verify(envioRepository, Mockito.times(1)).envioPorEstado(estadoEnvio);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(envio, result.get(0));
    }

    @Test
    void filtrarPorEstadoEmpleadoNoExiste() {
        Integer cedula = 123456789;
        EstadoEnvioEnum estadoEnvio = EstadoEnvioEnum.ENTREGADO;

        when(empleadoRepository.findById(123)).thenReturn(Optional.empty());
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.envioService.filtrarPorEstado(estadoEnvio,cedula),
                "La cedula del empleado no existe"
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("La cedula del empleado no existe"));
    }
}


