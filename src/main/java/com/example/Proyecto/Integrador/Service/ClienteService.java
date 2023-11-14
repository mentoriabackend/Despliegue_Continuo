package com.example.Proyecto.Integrador.Service;

import com.example.Proyecto.Integrador.Exception.ApiRequestException;
import com.example.Proyecto.Integrador.Model.Cliente;
import com.example.Proyecto.Integrador.Model.Empleado;
import com.example.Proyecto.Integrador.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente){
        return this.clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Cliente cliente) {
        Integer cedula = cliente.getCedula();
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(cedula);
        if (clienteEncontrado.isPresent()) {
            Cliente clienteActualizado = clienteEncontrado.get();
            clienteActualizado.setNombre(cliente.getNombre());
            clienteActualizado.setApellido(cliente.getApellido());
            clienteActualizado.setCelular(cliente.getCelular());
            clienteActualizado.setCorreoElectronico(cliente.getCorreoElectronico());
            clienteActualizado.setDireccion(cliente.getDireccion());
            clienteActualizado.setCiudad(cliente.getCiudad());
            return clienteRepository.save(clienteActualizado);
        } else {
            throw new ApiRequestException("No se encontró un cliente con la cédula" + cedula);
        }
    }

    public Boolean eliminarCliente(Integer cedula) {
        this.clienteRepository.deleteById(cedula);
        return true;
    }

    public Optional<Cliente> obtenerClienteCedula(Integer cedula){
        return this.clienteRepository.findById(cedula);
    }
}
