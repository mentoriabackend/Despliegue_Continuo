package com.example.Proyecto.Integrador.Controller;

import com.example.Proyecto.Integrador.Model.Cliente;
import com.example.Proyecto.Integrador.Model.ErrorResponse;
import com.example.Proyecto.Integrador.Service.ClienteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@Api(value = "Clientes", description = "Controlador para gestionar los clientes")
public class ClienteController {
    private final ClienteService clienteService;
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping("/cliente")
    @ApiOperation(value = "Crear un nuevo cliente", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El cliente ha sido creado exitosamente", response = Cliente.class),
            @ApiResponse(code = 400, message = "La solicitud es incorrecta", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)})
    @PreAuthorize("hasRole('WRITE')")
    public Cliente crearCliente (@RequestBody Cliente cliente){
        return this.clienteService.crearCliente(cliente);
    }

    @PutMapping("/cliente")
    @ApiOperation(value = "Actualizar un cliente existente", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El cliente ha sido actualizado exitosamente", response = Cliente.class),
            @ApiResponse(code = 400, message = "La solicitud es incorrecta", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "El cliente no existe", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('WRITE')")
    public Cliente actualizarEmpleado(@RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(cliente);
    }

    @DeleteMapping("/cliente/{cedula}")
    @ApiOperation(value = "Eliminar un cliente por cédula", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "El cliente ha sido eliminado exitosamente"),
            @ApiResponse(code = 404, message = "El cliente no existe", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('WRITE')")
    public String eliminarCliente(@ApiParam(value = "Este parámetro requiere la cédula del cliente a eliminar", required = true)@PathVariable Integer cedula) {
        this.clienteService.eliminarCliente(cedula);
        return "El cliente con cedula " + cedula + " fue eliminado con exito";
    }
//
    @GetMapping("/cliente/{cedula}")
    @ApiOperation(value = "Obtener un cliente por cédula", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El cliente ha sido obtenido exitosamente", response = Cliente.class),
            @ApiResponse(code = 404, message = "El cliente no existe", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('READ')")
    public Optional<Cliente> obtenerClienteCedula(@ApiParam(value = "Este parámetro requiere la cédula del cliente a buscar", required = true)@PathVariable Integer cedula){
        return this.clienteService.obtenerClienteCedula(cedula);
    }
}
