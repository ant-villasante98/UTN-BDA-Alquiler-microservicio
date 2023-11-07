package com.utn.bda.alquiler.domain.service.Implementacion;

import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.model.Estacion;
import com.utn.bda.alquiler.domain.repository.AlquilerRepository;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import com.utn.bda.alquiler.domain.service.EstacionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerSevice {
    private final AlquilerRepository alquilerRepository;

    private final EstacionService estacionService;
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionService estacionService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
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
        //TODO: Calcular monto
        //TODO: Calcular distacia
        Alquiler alquiler = new Alquiler(alquilerId,idCliente,estado,estacionRetiro,estacionDevolucion,fechaHoraRetiro,fechaHoraDevolucion,monto,idTarifar);
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

    }
}
