package com.example.Proyecto.Integrador.Dto;

import java.io.Serializable;
import java.time.LocalDate;

public class EmpleadoDto implements Serializable {
    private Integer cedula;
    private String nombre;
    private String apellido;
    private Long celular;
    private String correoElectronico;
    private String direccion;
    private String ciudad;
    private LocalDate antiguedad;
    private String tipoSangre;
    private String tipoEmpleado;

    public EmpleadoDto() {
    }

    public EmpleadoDto(Integer cedula, String nombre, String apellido, Long celular, String correoElectronico, String direccion, String ciudad, LocalDate antiguedad, String tipoSangre, String tipoEmpleado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.antiguedad = antiguedad;
        this.tipoSangre = tipoSangre;
        this.tipoEmpleado = tipoEmpleado;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(LocalDate antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
}
