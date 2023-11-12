package com.utn.bda.alquiler.infrastructure.service;

import com.utn.bda.alquiler.domain.model.Estacion;
import com.utn.bda.alquiler.domain.service.EstacionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstacionServiceImplTest {

    EstacionService estacionService;

    @BeforeEach
    public void iniciartest(){
        this.estacionService = new EstacionServiceImpl();
    }

    @Test
    public void consultarEstacionTest(){
        Estacion estacion =this.estacionService.getById(2);
        System.out.println(estacion);
        Assertions.assertEquals(estacion.getNombre(),"Comedor");
    }

}