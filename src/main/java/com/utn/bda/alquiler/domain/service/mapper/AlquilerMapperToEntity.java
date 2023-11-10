package com.utn.bda.alquiler.domain.service.mapper;

import com.utn.bda.alquiler.application.response.AlquilerResponse;
import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.model.Tarifa;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class AlquilerMapperToEntity implements Function<AlquilerResponse, Alquiler> {

    @Override
    public Alquiler apply(AlquilerResponse alquilerResponse) {
        return new Alquiler(
                alquilerResponse.getId(),
                alquilerResponse.getIdCliente(),
                alquilerResponse.getEstado(),
                alquilerResponse.getEstacionRetiro(),
                alquilerResponse.getEstacionDevolucion(),
                alquilerResponse.getFechaHoraRetiro(),
                alquilerResponse.getFechaHoraDevolucion(),
                alquilerResponse.getMonto(),
                Tarifa.builder().id(alquilerResponse.getId()).build()
        );
    }
}
