package com.example.Proyecto.Integrador.Dto;


import com.example.Proyecto.Integrador.Model.EstadoEnvioEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class EnvioDto implements Serializable {
    @ApiModelProperty(value = "La cédula del remitente", example = "12345", required = true)
    private Integer cedula;

    @ApiModelProperty(value = "El nombre del remitente", example = "Mateo", required = true)
    private String nombreRemitente;

    @ApiModelProperty(value = "La ciudad de origen del envío", example = "Bogotá", required = true)
    private String ciudadOrigen;

    @ApiModelProperty(value = "La ciudad de destino del envío", example = "Medellín", required = true)
    private String ciudadDestino;

    @ApiModelProperty(value = "La dirección de destino del envío", example = "Carrera 5 #25-28", required = true)
    private String direccionDestino;

    @ApiModelProperty(value = "El nombre de la persona que recibe el envío", example = "María Pérez", required = true)
    private String nombrePersona;

    @ApiModelProperty(value = "El número de teléfono de la persona que recibe el envío", example = "32145", required = true)
    private Integer numeroPersona;

    @ApiModelProperty(value = "El peso del envío en kilogramos", example = "2.5", required = true)
    private Double peso;

    private EstadoEnvioEnum estadoEnvio;

    @ApiModelProperty(value = "El valor declarado del envío", example = "15000", required = true)
    private Integer valorDeclarado;

    private Integer valorEnvio;

    public EnvioDto() {
    }

    public EnvioDto(Integer cedula, String nombreRemitente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombrePersona, Integer numeroPersona, Double peso, EstadoEnvioEnum estadoEnvio, Integer valorDeclarado) {
        this.cedula = cedula;
        this.nombreRemitente = nombreRemitente;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombrePersona = nombrePersona;
        this.numeroPersona = numeroPersona;
        this.peso = peso;
        this.estadoEnvio = estadoEnvio;
        this.valorDeclarado = valorDeclarado;
    }


}
