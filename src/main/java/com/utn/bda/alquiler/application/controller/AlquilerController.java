package com.utn.bda.alquiler.application.controller;


import com.utn.bda.alquiler.application.request.AlquilerCreateRequest;
import com.utn.bda.alquiler.application.request.AlquilerEndRequest;
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
    public ResponseEntity<List<AlquilerResponse>> getAll(){
        List<Alquiler> alquileres = this.alquilerSevice.findAll();
        List<AlquilerResponse> alquilerResponses = alquileres.stream().map(alquilerMapperToDto).toList();
        return new ResponseEntity<>(alquilerResponses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquilerResponse> getById(@PathVariable Integer id){
        try {
            Alquiler alquiler = this.alquilerSevice.findById(id).orElseThrow();
            AlquilerResponse alquilerResponse = this.alquilerMapperToDto.apply(alquiler);
            return new ResponseEntity<>(alquilerResponse,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AlquilerResponse> createAlquiler(@RequestBody AlquilerCreateRequest alquilerCreate){
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
            return new ResponseEntity<>(alquilerResponse,HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/por-estacionretiro/{idEstacion}")
    public ResponseEntity<List<AlquilerResponse>> alquileresPorEstacionRetiro(@PathVariable Integer idEstacion){
        try{
            List<Alquiler> alquileres = this.alquilerSevice.alquileresByEstacionRetiro(idEstacion);
            List<AlquilerResponse> alquilerResponses = alquileres.stream().map(alquilerMapperToDto).toList();
            return new ResponseEntity<>(alquilerResponses,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/finalizar-alquiler/{id}")
    public ResponseEntity<Object> finalizarAlquiler(@PathVariable Integer id , @RequestBody AlquilerEndRequest alquilerEnd){

        try {

            // Implementar finalizacion de alquiler
            Alquiler alquiler = this.alquilerSevice.finalizarAlquiler(id, alquilerEnd.getFechaHoraDevolucion(),alquilerEnd.getEstacionDevolucion());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
