package com.utn.bda.alquiler.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlquilerCreateRequest {
    private String idCliente;

    private Integer estado;

    private Integer estacionRetiro;

    private LocalDateTime fechaHoraRetiro;

    private Integer idTarifa;

    public Integer getEstado(){
        return 1;
    }
    public LocalDateTime getFechaHoraRetiro(){
        return LocalDateTime.now();
    }
}
