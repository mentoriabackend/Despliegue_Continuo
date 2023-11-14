package com.example.Proyecto.Integrador;

import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.Cliente;
import com.example.Proyecto.Integrador.Repository.ClienteRepository;
import com.example.Proyecto.Integrador.Service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {
    ClienteRepository clienteRepository;
    ClienteService clienteService;

    @BeforeEach
    void SetUp() {
        this.clienteRepository = mock(ClienteRepository.class);
        this.clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void CrearClienteExitosamente() {
        //Arrange
        Cliente cliente = new Cliente(123, "mateo", "Perez", 1234567L,
                "mateop@gmail.com", "calle 55 #5-65", "Bogota");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        clienteRepository.save(cliente);
        Cliente cliente1 = clienteService.crearCliente(cliente);

        Assertions.assertSame(cliente1, cliente);
    }

    @Test
    void actualizarCliente() {
        Integer cedula = 12345;
        //Act
        Cliente clienteExistente = new Cliente(cedula, "Mateo", "Restrepo", 12345L, "mateor@gmail.com",
                "Calle 451", "Ciudad");
        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(clienteExistente));

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula(cedula);
        clienteActualizado.setNombre(clienteExistente.getNombre());
        clienteActualizado.setApellido(clienteExistente.getApellido());
        clienteActualizado.setCelular(clienteExistente.getCelular());
        clienteActualizado.setCorreoElectronico(clienteExistente.getCorreoElectronico());
        clienteActualizado.setDireccion(clienteExistente.getDireccion());
        clienteActualizado.setCiudad(clienteExistente.getDireccion());

        // Act
        Cliente clienteActualizado2 = clienteService.actualizarCliente(clienteActualizado);

        // Verify
        verify(clienteRepository, times(1)).findById(cedula);

    }

    @Test
    void actualizarClienteSinRegistrar() {
        Cliente cliente = new Cliente(null, "Mateo", "perez", 12345L, "calle 56 6-56", "20", "d.perez@gmail.com");
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.clienteService.actualizarCliente(cliente),
                "No se encontró un cliente con la cédula" + cliente.getCedula()
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("No se encontró un cliente con la cédula" + cliente.getCedula()));
    }

    @Test
    void eliminarCliente() {
        //Arrange
        Integer cedula = 12345;
        doNothing().when(clienteRepository).deleteById(cedula);
        //Act
        Boolean resultado = clienteService.eliminarCliente(cedula);
        // Verify
        verify(clienteRepository, times(1)).deleteById(cedula);
        //Assert
        Assertions.assertTrue(resultado);
    }

    @Test
    void obtenerClienteCorrectoConCedula() {
        Cliente cliente = new Cliente(123, "mateo", "Perez", 1234567L,
                "mateop@gmail.com", "calle 55 #5-65", "Bogota");
        when(clienteRepository.findById(123)).thenReturn(Optional.of(cliente));
        clienteRepository.save(cliente);

        Optional<Cliente> clienteEncontrado = clienteService.obtenerClienteCedula(123);

        Assertions.assertTrue(clienteEncontrado.isPresent());
        Assertions.assertSame(cliente, clienteEncontrado.get());

    }
}


