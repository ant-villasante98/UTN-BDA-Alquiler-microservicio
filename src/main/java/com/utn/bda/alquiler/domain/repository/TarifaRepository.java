package com.utn.bda.alquiler.domain.repository;

import com.utn.bda.alquiler.domain.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa,Integer> {

}
