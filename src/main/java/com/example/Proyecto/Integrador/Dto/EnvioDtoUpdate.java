package com.example.Proyecto.Integrador.Dto;

import com.example.Proyecto.Integrador.Model.EstadoEnvioEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDtoUpdate {
    @ApiModelProperty(value = "La cédula del remitente", example = "123456789")
    private Integer cedula;

    @ApiModelProperty(value = "El número de guía del envío", example = "00123456789")
    private Integer numGuia;

    @ApiModelProperty(value = "El estado del envío", example = "EN RUTA")
    private EstadoEnvioEnum estadoEnvio;

}
