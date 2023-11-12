package com.utn.bda.alquiler.infrastructure.service;

import com.utn.bda.alquiler.domain.model.serviciosExternos.ExchangeResponse;
import com.utn.bda.alquiler.domain.model.serviciosExternos.ExchangeRequest;
import com.utn.bda.alquiler.domain.model.serviciosExternos.MonedaExchange;
import com.utn.bda.alquiler.domain.service.ExchangeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private  String urlExchange = "http://34.82.105.125:8080/convertir";


    @Override
    public ExchangeResponse convertirMoneda(String monedaDestino, Float importe) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        if (!MonedaExchange.monedas.contains(monedaDestino)){
            throw new Exception("El codigo de moneda destino no es valido");
        }
        ExchangeRequest exchangeRequest = new ExchangeRequest(monedaDestino,importe);
        HttpEntity<ExchangeRequest> entity = new HttpEntity<>(exchangeRequest);

        ResponseEntity<ExchangeResponse> response = restTemplate.postForEntity(this.urlExchange, entity, ExchangeResponse.class);
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }
        else {

            throw  new Exception("No se pudo realizar la consulta");
        }
    }
}
