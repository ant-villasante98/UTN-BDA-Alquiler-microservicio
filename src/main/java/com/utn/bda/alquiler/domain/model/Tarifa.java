package com.utn.bda.alquiler.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarifa {
    @Id
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

    @OneToMany(mappedBy = "tarifa")
    private List<Alquiler> alquilers;
}
