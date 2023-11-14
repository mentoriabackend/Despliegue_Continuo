package com.example.Proyecto.Integrador.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@Entity
@Table(name = "paquetes")
public class Paquete implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn
    private Integer idPaquete;
    @Enumerated(EnumType.STRING)
    private TipoPaqueteEnum tipoPaquete;

    @ApiModelProperty(value = "El peso del envío en kilogramos")
    private Double peso;

    @ApiModelProperty(value = "El valor declarado del envío")
    private Integer valorDeclarado;

    public Paquete() {
    }

    public Paquete(TipoPaqueteEnum tipoPaquete, Double peso, Integer valorDeclarado) {
        this.tipoPaquete = tipoPaquete;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }

    public static TipoPaqueteEnum asignarTipoPaquete(Double peso) {
        if (peso < 2.0) {
            return TipoPaqueteEnum.LIVIANO;
        } else if (peso > 2.0 && peso < 5.0) {
            return TipoPaqueteEnum.MEDIANO;
        }
        return TipoPaqueteEnum.GRANDE;
    }


}
