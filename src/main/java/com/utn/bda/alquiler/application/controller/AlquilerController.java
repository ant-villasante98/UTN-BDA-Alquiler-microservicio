package com.utn.bda.alquiler.application.controller;


import com.utn.bda.alquiler.application.request.AlquilerCreateRequest;
import com.utn.bda.alquiler.domain.model.Alquiler;
import com.utn.bda.alquiler.domain.service.AlquilerSevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alquileres")
public class AlquilerController {

    private final AlquilerSevice alquilerSevice;

    public AlquilerController(AlquilerSevice alquilerSevice) {
        this.alquilerSevice = alquilerSevice;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<Alquiler> alquileres = this.alquilerSevice.findAll();
        return new ResponseEntity<>(alquileres,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id){
        try {
            Alquiler alquiler = this.alquilerSevice.findById(id).orElseThrow();
            return new ResponseEntity<>(alquiler,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("No se encontro el recurso",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createAlquiler(@RequestBody AlquilerCreateRequest alquilerCreate){
        try {
            Alquiler alquiler = this.alquilerSevice
                    .create(alquilerCreate.getIdCliente(),
                            alquilerCreate.getEstado(),
                            alquilerCreate.getEstacionRetiro(),
                            null,
                            alquilerCreate.getFechaHoraRetiro(),
                            null,
                            null,
                            alquilerCreate.getIdTarifa());
            return new ResponseEntity<>(alquiler , HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
