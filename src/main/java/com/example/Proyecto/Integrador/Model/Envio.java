package com.example.Proyecto.Integrador.Model;


import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter @Setter
@Entity
@Table(name = "envios")
public class Envio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numGuia;
    @JoinColumn
    @ManyToOne
    private Cliente cliente;

    @ApiModelProperty(value = "La ciudad de origen del envío")
    private String ciudadOrigen;

    @ApiModelProperty(value = "La ciudad de destino del envío")
    private String ciudadDestino;

    @ApiModelProperty(value = "La dirección de destino del envío")
    private String direccionDestino;

    @ApiModelProperty(value = "El nombre de la persona que recibe el envío")
    private String nombrePersona;

    @ApiModelProperty(value = "El número de teléfono de la persona que recibe el envío")
    private Integer numeroPersona;

    private String horaEntrega;

    @Enumerated(EnumType.STRING)
    private EstadoEnvioEnum estadoEnvio;

    private Integer valorEnvio;
    @JoinColumn
    @OneToOne
    private Paquete paquete;

    public Envio() {
    }

    public Envio( Cliente cliente, String ciudadOrigen, String ciudadDestino, String direccionDestino, String nombrePersona, Integer numeroPersona, String horaEntrega, EstadoEnvioEnum estadoEnvio, Integer valorEnvio, Paquete paquete) {
        this.cliente = cliente;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
        this.nombrePersona = nombrePersona;
        this.numeroPersona = numeroPersona;
        this.horaEntrega = horaEntrega;
        this.estadoEnvio = estadoEnvio;
        this.valorEnvio = valorEnvio;
        this.paquete = paquete;
    }


    public static Integer asignarPrecioEnvio(TipoPaqueteEnum tipoPaqueteEnum) {
        switch (tipoPaqueteEnum) {
            case GRANDE:
                return 50000;
            case MEDIANO:
                return 40000;
            case LIVIANO:
                return 30000;
            default:
                throw new ApiRequestException("El tipo de paquete no corresponde");
        }
    }

}
