package com.utn.bda.alquiler.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alquileres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {
    @Id
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
