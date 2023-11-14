package com.utn.bda.alquiler.domain.service.Implementacion;

import com.utn.bda.alquiler.domain.repository.AlquilerRepository;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import com.utn.bda.alquiler.infrastructure.service.EstacionServiceImpl;
import com.utn.bda.alquiler.infrastructure.service.ExchangeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AlquilerServiceImplTest {

    private AlquilerServiceImpl alquilerSevice;

    @BeforeEach
    public void inciar(){
        AlquilerRepository alquilerRepository = Mockito.mock(AlquilerRepository.class);
        this.alquilerSevice = new AlquilerServiceImpl(alquilerRepository, new EstacionServiceImpl(),new ExchangeServiceImpl());
    }


    @Test
    public void calcularmonto(){
    }
}