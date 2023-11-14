package com.utn.bda.alquiler.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerResponse {
    private Integer id;

    private String idCliente;

    private Integer estado;

    private Integer estacionRetiro;

    private Integer estacionDevolucion;

    private LocalDateTime fechaHoraRetiro;

    private LocalDateTime fechaHoraDevolucion;

    private Float monto;
    private Integer idTarifa;
}
