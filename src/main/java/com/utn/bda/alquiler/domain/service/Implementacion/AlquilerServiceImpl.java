package com.utn.bda.alquiler.domain.service.Implementacion;

import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.model.Estacion;
import com.utn.bda.alquiler.domain.model.Tarifa;
import com.utn.bda.alquiler.domain.repository.AlquilerRepository;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import com.utn.bda.alquiler.domain.service.EstacionService;
import com.utn.bda.alquiler.domain.service.ExchangeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerSevice {
    private final AlquilerRepository alquilerRepository;

    // Servicio para obtener las estaciones
    private final EstacionService estacionService;

    // Servicio para convertir el importe
    private final ExchangeService exchangeService;
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionService estacionService, ExchangeService exchangeService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
        this.exchangeService = exchangeService;
    }

    @Override
    public Optional<Alquiler> findById(Integer id) {
        //prueba
        try {
            Estacion estacion = this.estacionService.getById(2);
            System.out.println(estacion.getNombre());
            System.out.println(estacion);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this.alquilerRepository.findById(id);

    }

    @Override
    public Alquiler create(String idCliente,
                           Integer estado,
                           Integer estacionRetiro,
                           Integer estacionDevolucion,
                           LocalDateTime fechaHoraRetiro,
                           LocalDateTime fechaHoraDevolucion,
                           Float monto,
                           Integer idTarifar) {
        Integer maxAlquilerId = this.alquilerRepository.getMaxAlquilerId();
        if(maxAlquilerId==null){
            maxAlquilerId = 0;
        }
        Integer alquilerId = maxAlquilerId+1;
        Alquiler alquiler = new Alquiler(alquilerId,idCliente,estado,estacionRetiro,estacionDevolucion,fechaHoraRetiro,fechaHoraDevolucion,monto, Tarifa.builder().id(idTarifar).build());
        return this.alquilerRepository.save(alquiler);
    }

    @Override
    public List<Alquiler> findAll() {
        return this.alquilerRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        Optional<Alquiler> alquiler = this.findById(id);
        this.alquilerRepository.delete(alquiler.orElseThrow());
    }

    @Override
    public void update(Integer id,
                       String idCliente,
                       Integer estado,
                       Integer estacionRetiro,
                       Integer estacionDevolucion,
                       LocalDateTime fechaHoraRetiro,
                       LocalDateTime fechaHoraDevolucion,
                       Float monto,
                       Integer idTarifar) {
        // Falta implementar
    }

    @Override
    public List<Alquiler> alquileresByEstacionRetiro(Integer idEstacion) {
        return this.alquilerRepository.findByEstacionRetiro(idEstacion);
    }
}
