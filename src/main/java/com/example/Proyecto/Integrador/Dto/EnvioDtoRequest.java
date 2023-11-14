package com.example.Proyecto.Integrador.Dto;

import com.example.Proyecto.Integrador.Model.EstadoEnvioEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioDtoRequest {
    private Integer numGuia;
    private EstadoEnvioEnum estadoEnvio;

    public EnvioDtoRequest() {
    }

    public EnvioDtoRequest(Integer numGuia, EstadoEnvioEnum estadoEnvio) {
        this.numGuia = numGuia;
        this.estadoEnvio = estadoEnvio;
    }
}
