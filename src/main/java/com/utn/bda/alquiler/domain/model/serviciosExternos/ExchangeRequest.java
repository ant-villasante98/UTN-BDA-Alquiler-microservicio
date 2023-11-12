package com.utn.bda.alquiler.domain.model.serviciosExternos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {
    private String moneda_destino;
    private Float importe;

}
