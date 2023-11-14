package com.example.Proyecto.Integrador;

import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.Empleado;
import com.example.Proyecto.Integrador.Repository.EmpleadoRepository;
import com.example.Proyecto.Integrador.Service.EmpleadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class EmpleadoServiceTest {
    EmpleadoRepository empleadoRepository;
    EmpleadoService empleadoService;

    @BeforeEach
    void SetUp() {
        this.empleadoRepository = mock(EmpleadoRepository.class);
        this.empleadoService = new EmpleadoService(empleadoRepository);
    }

    @Test
    void crearEmpleadoExitosamente() {
        //Arrange
        Empleado empleado = new Empleado(123, "mateo", "Perez", 1234567L,
                "mateop@gmail.com", "calle 55 #5-65", "Bogota");
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);
        empleadoRepository.save(empleado);
        //Act
        Empleado empleado1 = empleadoService.crearEmpleado(empleado);
        //Assert
        Assertions.assertSame(empleado1, empleado);
    }

    @Test
    void actualizarEmpleado() {
        //Arrange
        Integer cedula = 12345;
        Empleado empleadoExistente = new Empleado(cedula, "Mateo", "Restrepo", 12345L, "mateor@gmail.com",
                "Calle 451", "Ciudad");
        when(empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleadoExistente));

        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setCedula(cedula);
        empleadoActualizado.setNombre(empleadoExistente.getNombre());
        empleadoActualizado.setApellido(empleadoExistente.getApellido());
        empleadoActualizado.setCelular(empleadoExistente.getCelular());
        empleadoActualizado.setCorreoElectronico(empleadoExistente.getCorreoElectronico());
        empleadoActualizado.setDireccion(empleadoExistente.getDireccion());
        empleadoActualizado.setCiudad(empleadoExistente.getDireccion());

        // Act
        Empleado empleadoActualizado2 = empleadoService.actualizarEmpleado(empleadoActualizado);
        // Verify
        verify(empleadoRepository, times(1)).findById(cedula);
    }

    @Test
    void actualizarEmpleadoConCedulaQueNoExiste() {
        Empleado empleado = new Empleado(null, "mateo", "Perez", 1234567L,
                "mateop@gmail.com", "calle 55 #5-65", "Bogota");
        ApiRequestException thrown = assertThrows(
                ApiRequestException.class,
                () -> this.empleadoService.actualizarEmpleado(empleado),
                "No se encontró un empleado con la cédula" + empleado.getCedula()
        );
        Assertions.assertTrue(thrown.getMessage().contentEquals("No se encontró un empleado con la cédula" + empleado.getCedula()));
    }

    @Test
    void eliminarEmpleado() {
        //Arrange
        Integer cedula = 12345;
        doNothing().when(empleadoRepository).deleteById(cedula);
        //Act
        Boolean resultado = empleadoService.eliminarEmpleado(cedula);
        // Verify
        verify(empleadoRepository, times(1)).deleteById(cedula);
        //Assert
        Assertions.assertTrue(resultado);
    }

    @Test
    void obtenerEmpleadoCorrectoConCedula() {
        Empleado empleado = new Empleado(123, "mateo", "Perez", 1234567L,
                "mateop@gmail.com", "calle 55 #5-65", "Bogota");
        when(empleadoRepository.findById(123)).thenReturn(Optional.of(empleado));
        empleadoRepository.save(empleado);

        Optional<Empleado> clienteEncontrado = empleadoService.obtenerEmpleadoCedula(123);

        Assertions.assertTrue(clienteEncontrado.isPresent());
        Assertions.assertSame(empleado, clienteEncontrado.get());

    }
}
