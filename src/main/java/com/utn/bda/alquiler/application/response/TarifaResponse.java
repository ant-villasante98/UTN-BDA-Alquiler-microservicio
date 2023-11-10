package com.utn.bda.alquiler.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TarifaResponse {
    private Integer id;

    private Integer tipoTarifa;

    private String definicion;

    private Integer diaSemana;

    private Integer diaMes;

    private Integer mes;

    private Integer anio;

    private Float montoFijoAlquiler;

    private Float montoMinutoFraccion;

    private Float montoKm;

    private Float montoHora;
}
