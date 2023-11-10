package com.utn.bda.alquiler.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlquilerCreateRequest {
    private String idCliente;


    private Integer estacionRetiro;


    private Integer idTarifa;


}
