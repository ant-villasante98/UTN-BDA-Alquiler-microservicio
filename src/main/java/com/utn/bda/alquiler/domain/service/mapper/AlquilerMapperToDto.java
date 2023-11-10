package com.utn.bda.alquiler.domain.service.mapper;

import com.utn.bda.alquiler.application.response.AlquilerResponse;
import com.utn.bda.alquiler.domain.model.Alquiler;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AlquilerMapperToDto implements Function<Alquiler, AlquilerResponse> {

    private final TarifaMapperToDto tarifaMapperToDto;

    public AlquilerMapperToDto(TarifaMapperToDto tarifaMapperToDto) {
        this.tarifaMapperToDto = tarifaMapperToDto;
    }

    @Override
    public AlquilerResponse apply(Alquiler alquiler) {
        return new AlquilerResponse(
                alquiler.getId(),
                alquiler.getIdCliente(),
                alquiler.getEstado(),
                alquiler.getEstacionRetiro(),
                alquiler.getEstacionDevolucion(),
                alquiler.getFechaHoraRetiro(),
                alquiler.getFechaHoraDevolucion(),
                alquiler.getMonto(),
                tarifaMapperToDto.apply(alquiler.getTarifa())
        );
    }
}
