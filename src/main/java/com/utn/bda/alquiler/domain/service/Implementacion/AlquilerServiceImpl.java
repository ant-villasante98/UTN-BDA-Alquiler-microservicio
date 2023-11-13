package com.utn.bda.alquiler.domain.service.Implementacion;

import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.model.Estacion;
import com.utn.bda.alquiler.domain.model.Tarifa;
import com.utn.bda.alquiler.domain.repository.AlquilerRepository;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import com.utn.bda.alquiler.domain.service.EstacionService;
import com.utn.bda.alquiler.domain.service.ExchangeService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
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
        Optional<Alquiler> existingAlquiler = this.alquilerRepository.findById(id);

        if (existingAlquiler.isPresent()) {
            Alquiler alquiler = existingAlquiler.get();
            alquiler.setIdCliente(idCliente);
            alquiler.setEstado(estado);
            alquiler.setEstacionRetiro(estacionRetiro);
            alquiler.setEstacionDevolucion(estacionDevolucion);
            alquiler.setFechaHoraRetiro(fechaHoraRetiro);
            alquiler.setFechaHoraDevolucion(fechaHoraDevolucion);
            alquiler.setMonto(monto);
            alquiler.setTarifa(Tarifa.builder().id(idTarifar).build());

            this.alquilerRepository.save(alquiler);
        }
    }

    @Override
    public List<Alquiler> alquileresByEstacionRetiro(Integer idEstacion) {
        return this.alquilerRepository.findByEstacionRetiro(idEstacion);
    }

    // Método para calcular el monto del alquiler
    public double calcularMontoAlquiler(Alquiler alquiler) {
        // Obtener la información de las tablas relevantes en la base de datos
        // (costos por día, precio adicional por kilómetro, días promocionales, etc.)

        // Calcular la duración del alquiler en minutos
        Duration duracionAlquiler = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
        Long minutosAlquiler = duracionAlquiler.toMinutes();

        // Calcular el costo fijo del alquiler
        double costoFijo = obtenerCostoFijoPorDia(alquiler.getFechaHoraRetiro().getDayOfWeek());

        // Calcular el costo por hora completa
        double costoPorHoraCompleta = calcularCostoPorHoraCompleta(minutosAlquiler);

        // Calcular el costo adicional por kilómetro
        double costoAdicionalPorKm = obtenerCostoAdicionalPorKm();

        // Calcular la distancia entre las estaciones de retiro y devolución
        double distanciaEnKm = calcularDistanciaEntreEstaciones(alquiler.getEstacionRetiro(), alquiler.getEstacionDevolucion());

        // Calcular el monto total antes de aplicar el descuento
        double montoTotal = costoFijo + costoPorHoraCompleta + (costoAdicionalPorKm * distanciaEnKm);

        // Aplicar descuento si el día de retiro es un día promocional
        if (esDiaPromocional(alquiler.getFechaHoraRetiro().getDayOfWeek())) {
            double porcentajeDescuento = obtenerPorcentajeDescuento();
            double montoDescuento = (porcentajeDescuento / 100) * montoTotal;
            montoTotal -= montoDescuento;
        }

        return montoTotal;
    }

    // Métodos auxiliares (implementa estos métodos según tus necesidades)
    private double obtenerCostoFijoPorDia(DayOfWeek dayOfWeek) {
        // Implementa la lógica para obtener el costo fijo por día
        // desde la base de datos o cualquier otra fuente de datos
        return 0.0;
    }

    private double calcularCostoPorHoraCompleta(Long minutos) {
        // Implementa la lógica para calcular el costo por hora completa
        // desde la base de datos o cualquier otra fuente de datos
        return 0.0;
    }

    private double obtenerCostoAdicionalPorKm() {
        // Implementa la lógica para obtener el costo adicional por kilómetro
        // desde la base de datos o cualquier otra fuente de datos
        return 0.0;
    }

    private double calcularDistanciaEntreEstaciones(Estacion estacionRetiro, Estacion estacionDevolucion) {
        // Implementa la lógica para calcular la distancia entre estaciones
        // Puedes usar algoritmos de cálculo de distancia o servicios externos
        return 0.0;
    }

    private boolean esDiaPromocional(DayOfWeek dayOfWeek) {
        // Implementa la lógica para verificar si el día es promocional
        // desde la base de datos o cualquier otra fuente de datos
        return false;
    }

    private double obtenerPorcentajeDescuento() {
        // Implementa la lógica para obtener el porcentaje de descuento
        // desde la base de datos o cualquier otra fuente de datos
        return 0.0;
    }
}


