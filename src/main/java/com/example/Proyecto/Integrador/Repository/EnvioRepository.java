package com.example.Proyecto.Integrador.Repository;

import com.example.Proyecto.Integrador.Model.Envio;
import com.example.Proyecto.Integrador.Model.EstadoEnvioEnum;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    @Query("SELECT e FROM Envio e WHERE e.estadoEnvio = ?1")
    public List<Envio> envioPorEstado(EstadoEnvioEnum estadoEnvio);
}
