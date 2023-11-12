package com.utn.bda.alquiler.domain.model.serviciosExternos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeResponse {
    private String moneda;
    private Float importe;

    public ExchangeResponse(String moneda, Float importe){
        this.moneda = moneda;
        this.importe = importe;

    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Float getImporteRedondeado() {
        return Math.round(this.importe*100f)/100f;
    }
    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }
}
