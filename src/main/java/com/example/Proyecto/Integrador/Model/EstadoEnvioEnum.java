package com.example.Proyecto.Integrador.Model;

public enum EstadoEnvioEnum {
    RECIBIDO("RECIBIDO"),
    RUTA("EN RUTA"),
    ENTREGADO("ENTREGADO");

    private final String name;

    EstadoEnvioEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
