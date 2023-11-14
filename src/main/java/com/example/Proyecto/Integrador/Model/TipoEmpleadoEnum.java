package com.example.Proyecto.Integrador.Model;

public enum TipoEmpleadoEnum {
    REPARTIDOR("REPARTIDOR"),
    COORDINADOR("COORDINADOR");

    private final String name;

    TipoEmpleadoEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
