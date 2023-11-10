package com.utn.bda.alquiler.domain.model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "id_tarifa")
    private Tarifa tarifa;
}
