package com.utn.bda.alquiler.domain.repository;

import com.utn.bda.alquiler.domain.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlquilerRepository extends JpaRepository<Alquiler,Integer> {

    @Query(value = "SELECT MAX(a.ID)  FROM ALQUILERES a",nativeQuery = true)
    Integer getMaxAlquilerId();
}
