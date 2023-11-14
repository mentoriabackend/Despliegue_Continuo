package com.example.Proyecto.Integrador.Controller;

import com.example.Proyecto.Integrador.Dto.EnvioDto;
import com.example.Proyecto.Integrador.Dto.EnvioDtoRequest;
import com.example.Proyecto.Integrador.Dto.EnvioDtoUpdate;
import com.example.Proyecto.Integrador.Model.Envio;
import com.example.Proyecto.Integrador.Model.ErrorResponse;
import com.example.Proyecto.Integrador.Model.EstadoEnvioEnum;
import com.example.Proyecto.Integrador.Service.EnvioService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Envios", description = "Controlador para gestionar envíos")
public class EnvioController {
    private final EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping("/envio")
    @ApiOperation(value = "Crear un envío", response = Envio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Envío creado exitosamente", response = Envio.class),
            @ApiResponse(code = 400, message = "Petición inválida",response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('WRITE')")
    public EnvioDtoRequest crearEnvio(@RequestBody EnvioDto envio){
        return this.envioService.crearEnvio(envio);
    }

    @GetMapping("/envio/{numGuia}")
    @ApiOperation(value = "Obtener envío por número de guía", response = Envio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Envío encontrado", response = Envio.class),
            @ApiResponse(code = 404, message = "Envío no encontrado", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('READ')")
    public EnvioDto obtenerNumeroGuia(@ApiParam(value = "Este parámetro requiere el número de guía a buscar", required = true)@PathVariable Integer numGuia){
        return this.envioService.obtenerNumeroGuia(numGuia);
    }

    @PutMapping("/envio")
    @ApiOperation(value = "Actualizar un envío", response = Envio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Envío actualizado exitosamente", response = Envio.class),
            @ApiResponse(code = 404, message = "Envío no encontrado", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Petición inválida", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('WRITE')")
    public EnvioDtoRequest actualizarEstadoPaquete(@RequestBody EnvioDtoUpdate envioDtoUpdate){
        return this.envioService.actualizarEstadoPaquete(envioDtoUpdate);
    }

    @GetMapping("/envio")
    @ApiOperation(value = "Obtener envío por cédula de empleado y estado de énvio", response = Envio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Envíos encontrados exitosamente", response = Envio.class),
            @ApiResponse(code = 404, message = "Envios no encontrados", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Petición inválida", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ErrorResponse.class)
    })
    @PreAuthorize("hasRole('READ')")
    public List<Envio> filtrarPorEstado(@ApiParam(value = "Este parámetro requiere la cédula del empleado y el estado del envio", required = true)@RequestParam ("cedula") Integer cedula, @RequestParam ("estadoEnvio") EstadoEnvioEnum estadoEnvio){
        return this.envioService.filtrarPorEstado(estadoEnvio,cedula);
    }

}
