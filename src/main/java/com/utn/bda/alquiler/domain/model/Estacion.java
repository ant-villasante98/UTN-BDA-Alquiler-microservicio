package com.utn.bda.alquiler.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estacion {
    private Integer id;

    private String nombre;

    private LocalDateTime fechaHoraCreacion;

    private Float latitud;

    private Float longitud;
}
