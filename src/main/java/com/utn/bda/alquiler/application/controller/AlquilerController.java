package com.utn.bda.alquiler.application.controller;


import com.utn.bda.alquiler.application.ResponseHandler;
import com.utn.bda.alquiler.application.request.AlquilerCreateRequest;
import com.utn.bda.alquiler.application.response.AlquilerResponse;
import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import com.utn.bda.alquiler.domain.service.mapper.AlquilerMapperToDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alquileres")
public class AlquilerController {

    private final AlquilerSevice alquilerSevice;
    private final AlquilerMapperToDto alquilerMapperToDto;

    public AlquilerController(AlquilerSevice alquilerSevice, AlquilerMapperToDto alquilerMapperToDto) {
        this.alquilerSevice = alquilerSevice;
        this.alquilerMapperToDto = alquilerMapperToDto;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<Alquiler> alquileres = this.alquilerSevice.findAll();
        List<AlquilerResponse> alquilerResponses = alquileres.stream().map(alquilerMapperToDto).toList();
        return ResponseHandler.success(alquilerResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id){
        try {
            Alquiler alquiler = this.alquilerSevice.findById(id).orElseThrow();
            AlquilerResponse alquilerResponse = this.alquilerMapperToDto.apply(alquiler);
            return ResponseHandler.success(alquilerResponse);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("No se encontro el recurso",HttpStatus.NOT_FOUND,null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createAlquiler(@RequestBody AlquilerCreateRequest alquilerCreate){
        try {
            Alquiler alquiler = this.alquilerSevice
                    .create(alquilerCreate.getIdCliente(),
                            1,
                            alquilerCreate.getEstacionRetiro(),
                            null,
                            LocalDateTime.now(),
                            null,
                            null,
                            alquilerCreate.getIdTarifa());
            AlquilerResponse alquilerResponse = this.alquilerMapperToDto.apply(alquiler);
            return ResponseHandler.created(alquilerResponse);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseHandler.badRequest("No se pudo crear el recurso");
        }
    }

    @GetMapping("/por-estacionretiro/{idEstacion}")
    public ResponseEntity<Object> alquileresPorEstacionRetiro(@PathVariable Integer idEstacion){
        try{
            List<Alquiler> alquileres = this.alquilerSevice.alquileresByEstacionRetiro(idEstacion);
            List<AlquilerResponse> alquilerResponses = alquileres.stream().map(alquilerMapperToDto).toList();
            return ResponseHandler.success(alquilerResponses);
        }
        catch (Exception e){
            return ResponseHandler.badRequest("No se pudo realizar la consulta");
        }
    }

    // TODO: Utilizar @PatchMapping para finalizar la transaccion, debe de resibir un objeto en el Body
    //
    @PatchMapping("/finalizar-alquiler")
    public ResponseEntity<Object> finalizarAlquiler(){
        // Implementar finalizacion de alquiler
        return ResponseHandler.success(null);
    }
}
