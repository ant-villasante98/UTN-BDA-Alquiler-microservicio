package com.utn.bda.alquiler.domain;

import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.model.Estacion;

import java.time.Duration;

public class CalcularMontoAlquiler {
    public static float calcularMontoAlquiler(Alquiler alquiler , Estacion estacionRetiro , Estacion estacionDevolucion) {

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
        float costoAdicionalPorKm = obtenerCostoAdicionalPorKm(alquiler);

        // Calcular la distancia entre las estaciones de retiro y devolución
        float distanciaEnKm = calcularDistanciaEntreEstaciones(estacionRetiro, estacionDevolucion);

        // Calcular el monto total antes de aplicar el descuento
        float montoTotal = costoFijo + costoPorHoraCompleta + (costoAdicionalPorKm * distanciaEnKm);


        return Math.round(montoTotal*100f)/100f;
    }

    // Métodos auxiliares (implementa estos métodos según tus necesidades)
    private static float obtenerCostoFijoPorDia(Alquiler alquiler) {
        // Implementa la lógica para obtener el costo fijo por día
        // desde la base de datos o cualquier otra fuente de datos
        return  alquiler.getTarifa().getMontoFijoAlquiler();
    }

    private static float calcularCostoPorHoraCompleta(Long minutos, Alquiler alquiler) {
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

    private static float obtenerCostoAdicionalPorKm(Alquiler alquiler) {
        // Implementa la lógica para obtener el costo adicional por kilómetro
        // desde la base de datos o cualquier otra fuente de datos
        return alquiler.getTarifa().getMontoKm();

    }

    private static float calcularDistanciaEntreEstaciones(Estacion estacionRetiro, Estacion estacionDevolucion) {
        // Implementa la lógica para calcular la distancia entre estaciones
        // Puedes usar algoritmos de cálculo de distancia o servicios externos
        float distancia = calcularDistancia(estacionRetiro.getLatitud(), estacionRetiro.getLongitud(),
                estacionDevolucion.getLatitud(), estacionDevolucion.getLongitud());
        return distancia*11000;
    }

    private static float calcularDistancia(float latitud1, float longitud1, float latitud2, float longitud2) {
        // Aquí debes implementar el cálculo de la distancia entre dos coordenadas geográficas.
        // Puedes utilizar la fórmula de la distancia euclidiana para calcular la distancia.
        // Ten en cuenta que esta fórmula es una simplificación y no es 100% precisa, pero es suficiente para este ejemplo.
        float latitudDiferencia = latitud1 - latitud2;
        float longitudDiferencia = longitud1 - longitud2;
        return (float) Math.sqrt(latitudDiferencia * latitudDiferencia + longitudDiferencia * longitudDiferencia);
    }
}
