package com.utn.bda.alquiler.infrastructure.service;

import com.utn.bda.alquiler.domain.model.serviciosExternos.ExchangeResponse;
import com.utn.bda.alquiler.domain.service.ExchangeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceImplTest {
    ExchangeService exchangeService ;

    @BeforeEach
    public void iniciarTest(){
        this.exchangeService= new ExchangeServiceImpl();
    }

    @Test
    public void testConvertirMoneda() throws Exception {

        ExchangeResponse exchangeResponse = this.exchangeService.convertirMoneda("USD", 200f);
        System.out.println(exchangeResponse.toString()+" Redondeo: "+exchangeResponse.getImporteRedondeado().toString());

        Assertions.assertEquals(exchangeResponse.getImporte() , 0.5714448984256693f);
    }



}