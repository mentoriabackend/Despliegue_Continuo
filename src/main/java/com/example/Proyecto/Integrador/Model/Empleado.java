package com.example.Proyecto.Integrador.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "empleados")
public class Empleado extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "La fecha de antigüedad del empleado", example = "2020-01-01")
    private LocalDate antiguedad;

    @ApiModelProperty(value = "El tipo de sangre del empleado", example = "AB+")
    private String tipoSangre;

    @ApiModelProperty(value = "El tipo de empleado", example = "COORDINADOR")
    @Enumerated(EnumType.STRING)
    private TipoEmpleadoEnum tipoEmpleado;


    public Empleado() {
    }

    public Empleado(Integer cedula, String nombre, String apellido, Long celular, String correoElectronico, String direccion, String ciudad) {
        super(cedula, nombre, apellido, celular, correoElectronico, direccion, ciudad);
    }

}


