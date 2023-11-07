package com.utn.bda.alquiler.domain.service;


import com.utn.bda.alquiler.domain.model.Alquiler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlquilerSevice {
    Optional<Alquiler> findById(Integer id);

    Alquiler create(String idCliente,
                    Integer estado,
                    Integer estacionRetiro,
                    Integer estacionDevolucion,
                    LocalDateTime fechaHoraRetiro,
                    LocalDateTime fechaHoraDevolucion,
                    Float monto,
                    Integer idTarifar);

    List<Alquiler> findAll();

    void delete(Integer id);

    void update(Integer id,
                String idCliente,
                Integer estado,
                Integer estacionRetiro,
                Integer estacionDevolucion,
                LocalDateTime fechaHoraRetiro,
                LocalDateTime fechaHoraDevolucion,
                Float monto,
                Integer idTarifa);
}
