package com.example.Proyecto.Integrador.Model;

public enum TipoPaqueteEnum {
    LIVIANO("LIVIANO"),
    MEDIANO("MEDIANO"),
    GRANDE("GRANDE");

    private final String name;

    TipoPaqueteEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
