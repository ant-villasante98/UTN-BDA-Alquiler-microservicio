package com.utn.bda.alquiler.domain.service;

import com.utn.bda.alquiler.domain.model.serviciosExternos.ExchangeResponse;

public interface ExchangeService {
    ExchangeResponse convertirMoneda(String monedaDestino, Float importe) throws Exception;
}
