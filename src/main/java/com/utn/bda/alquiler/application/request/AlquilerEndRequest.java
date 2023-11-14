package com.utn.bda.alquiler.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerEndRequest {
    private Integer estacionDevolucion;
    private LocalDateTime fechaHoraDevolucion;
}
