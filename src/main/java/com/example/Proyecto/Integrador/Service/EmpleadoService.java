package com.example.Proyecto.Integrador.Service;

import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.Empleado;
import com.example.Proyecto.Integrador.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoService {

    private EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado crearEmpleado(Empleado empleado){
        return this.empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Empleado empleado) {
        Integer cedula = empleado.getCedula();
        Optional<Empleado> empleadoEncontrado = empleadoRepository.findById(cedula);
        if (empleadoEncontrado.isPresent()) {
            Empleado empleadoActualizado = empleadoEncontrado.get();
            empleadoActualizado.setNombre(empleado.getNombre());
            empleadoActualizado.setApellido(empleado.getApellido());
            empleadoActualizado.setCelular(empleado.getCelular());
            empleadoActualizado.setCorreoElectronico(empleado.getCorreoElectronico());
            empleadoActualizado.setDireccion(empleado.getDireccion());
            empleadoActualizado.setCiudad(empleado.getCiudad());
            return empleadoRepository.save(empleadoActualizado);
        } else {
            throw new ApiRequestException("No se encontró un empleado con la cédula" + cedula);
        }
    }

    public Boolean eliminarEmpleado(Integer cedula) {
        this.empleadoRepository.deleteById(cedula);
        return true;
    }

    public Optional<Empleado> obtenerEmpleadoCedula(Integer cedula){
        return this.empleadoRepository.findById(cedula);
    }
}
