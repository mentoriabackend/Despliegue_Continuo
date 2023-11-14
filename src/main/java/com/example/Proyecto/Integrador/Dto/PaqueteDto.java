package com.example.Proyecto.Integrador.Dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PaqueteDto implements Serializable {
    private Double peso;
    private Integer valorDeclarado;

    public PaqueteDto() {
    }

    public PaqueteDto(Double peso, Integer valorDeclarado) {
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }

    public Double getPeso() {
        return peso;
    }

    public Integer getValorDeclarado() {
        return valorDeclarado;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setValorDeclarado(Integer valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }
}
