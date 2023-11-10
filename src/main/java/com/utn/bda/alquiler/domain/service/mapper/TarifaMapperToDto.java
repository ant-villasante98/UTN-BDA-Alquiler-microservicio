package com.utn.bda.alquiler.domain.service.mapper;

import com.utn.bda.alquiler.application.response.TarifaResponse;
import com.utn.bda.alquiler.domain.model.Tarifa;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TarifaMapperToDto implements Function<Tarifa, TarifaResponse> {
    @Override
    public TarifaResponse apply(Tarifa tarifa) {
        return new TarifaResponse(
                tarifa.getId(),
                tarifa.getTipoTarifa(),
                tarifa.getDefinicion(),
                tarifa.getDiaSemana(),
                tarifa.getDiaMes(),
                tarifa.getMes(),
                tarifa.getAnio(),
                tarifa.getMontoFijoAlquiler(),
                tarifa.getMontoMinutoFraccion(),
                tarifa.getMontoKm(),
                tarifa.getMontoHora()
        );
    }
}
