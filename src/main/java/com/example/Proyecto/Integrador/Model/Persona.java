package com.example.Proyecto.Integrador.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter @Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @ApiModelProperty(value = "La cédula del usuario", example = "123456789", required = true)
    private Integer cedula;

    @ApiModelProperty(value = "El nombre del usuario", example = "Juan", required = true)
    private String nombre;

    @ApiModelProperty(value = "El apellido del usuario", example = "Pérez", required = true)
    private String apellido;

    @ApiModelProperty(value = "El número de celular del usuario", example = "3101234567", required = true)
    private Long celular;

    @ApiModelProperty(value = "El correo electrónico del usuario", example = "juan.perez@example.com", required = true)
    private String correoElectronico;

    @ApiModelProperty(value = "La dirección del usuario", example = "Carrera 10 #20-30", required = true)
    private String direccion;

    @ApiModelProperty(value = "La ciudad del usuario", example = "Bogotá", required = true)
    private String ciudad;

    public Persona(Integer cedula, String nombre, String apellido, Long celular, String correoElectronico, String direccion, String ciudad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

}
