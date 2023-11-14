package com.example.Proyecto.Integrador.Repository;

import com.example.Proyecto.Integrador.Model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete,Integer> {
}
