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
import java.util.Objects;
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
        Estacion estacionRetiro = this.estacionService.getById(alquiler.getEstacionRetiro());
        Estacion estacionDevolucion = this.estacionService.getById(alquiler.getEstacionDevolucion());
        // Obtener la información de las tablas relevantes en la base de datos
        // (costos por día, precio adicional por kilómetro, días promocionales, etc.)

        // Calcular la duración del alquiler en minutos
        Duration duracionAlquiler = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
        Long minutosAlquiler = duracionAlquiler.toMinutes();

        // Calcular el costo fijo del alquiler
        float costoFijo = obtenerCostoFijoPorDia(alquiler);

        // Calcular el costo por hora completa
        float costoPorHoraCompleta = calcularCostoPorHoraCompleta(minutosAlquiler, alquiler);

        // Calcular el costo adicional por kilómetro
        double costoAdicionalPorKm = obtenerCostoAdicionalPorKm();

        // Calcular la distancia entre las estaciones de retiro y devolución
        double distanciaEnKm = calcularDistanciaEntreEstaciones(estacionRetiro, estacionDevolucion);

        // Calcular el monto total antes de aplicar el descuento
        double montoTotal = costoFijo + costoPorHoraCompleta + (costoAdicionalPorKm * distanciaEnKm);

//        // Aplicar descuento si el día de retiro es un día promocional
//        if (esDiaPromocional(alquiler.getFechaHoraRetiro().getDayOfWeek())) {
//            double porcentajeDescuento = obtenerPorcentajeDescuento();
//            double montoDescuento = (porcentajeDescuento / 100) * montoTotal;
//            montoTotal -= montoDescuento;
//        }

        return montoTotal;
    }

    // Métodos auxiliares (implementa estos métodos según tus necesidades)
    private float obtenerCostoFijoPorDia(Alquiler alquiler) {
        // Implementa la lógica para obtener el costo fijo por día
        // desde la base de datos o cualquier otra fuente de datos
        return (float) alquiler.getTarifa().getMontoFijoAlquiler();
    }

    private float calcularCostoPorHoraCompleta(Long minutos, Alquiler alquiler) {
        // Implementa la lógica para calcular el costo por hora completa
        // desde la base de datos o cualquier otra fuente de datos
        long minutosFraccion = minutos % 60;
        long horas = minutos / 60;
        float costoPorHora;
        costoPorHora = horas * alquiler.getTarifa().getMontoHora();
        float costoPorMinuto = minutosFraccion * alquiler.getTarifa().getMontoMinutoFraccion();
        costoPorHora += costoPorMinuto;

        return costoPorHora;
    }

    private double obtenerCostoAdicionalPorKm() {
        // Implementa la lógica para obtener el costo adicional por kilómetro
        // desde la base de datos o cualquier otra fuente de datos
        return 0.0;
    }

    private double calcularDistanciaEntreEstaciones(Estacion estacionRetiro, Estacion estacionDevolucion) {
        // Implementa la lógica para calcular la distancia entre estaciones
        // Puedes usar algoritmos de cálculo de distancia o servicios externos
        double distancia = calcularDistancia(estacionRetiro.getLatitud(), estacionRetiro.getLongitud(),
                estacionDevolucion.getLatitud(), estacionDevolucion.getLongitud());
        return distancia*11000;
    }

    private double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
    // Aquí debes implementar el cálculo de la distancia entre dos coordenadas geográficas.
    // Puedes utilizar la fórmula de la distancia euclidiana para calcular la distancia.
    // Ten en cuenta que esta fórmula es una simplificación y no es 100% precisa, pero es suficiente para este ejemplo.
        double latitudDiferencia = latitud1 - latitud2;
        double longitudDiferencia = longitud1 - longitud2;
        return Math.sqrt(latitudDiferencia * latitudDiferencia + longitudDiferencia * longitudDiferencia);
    }

//    private boolean esDiaPromocional(DayOfWeek dayOfWeek) {
//        // Implementa la lógica para verificar si el día es promocional
//        // desde la base de datos o cualquier otra fuente de datos
//        return false;
//    }

//    private double obtenerPorcentajeDescuento() {
//        // Implementa la lógica para obtener el porcentaje de descuento
//        // desde la base de datos o cualquier otra fuente de datos
//        return 0.0;
//    }
}


