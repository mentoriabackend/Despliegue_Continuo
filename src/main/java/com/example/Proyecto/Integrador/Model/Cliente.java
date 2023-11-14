package com.example.Proyecto.Integrador.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona{

    public Cliente() {
    }

    public Cliente(Integer cedula, String nombre, String apellido, Long celular, String correoElectronico, String direccion, String ciudad) {
        super(cedula, nombre, apellido, celular, correoElectronico, direccion, ciudad);
    }


}
