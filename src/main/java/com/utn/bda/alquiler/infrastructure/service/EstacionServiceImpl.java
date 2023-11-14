package com.utn.bda.alquiler.infrastructure.service;

import com.utn.bda.alquiler.domain.model.Estacion;
import com.utn.bda.alquiler.domain.service.EstacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EstacionServiceImpl implements EstacionService {
    private final String resourceUrl = "http://localhost:8081/api/v1/estaciones";


    @Override
    public Estacion getById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Estacion> response = restTemplate.getForEntity(this.resourceUrl +"/{id}", Estacion.class,id);
        if(response.getStatusCode().is2xxSuccessful()){

            return response.getBody();
        }
        else {
            throw  new RuntimeException(" no se puco obtener el recurso");
        }


    }
}
