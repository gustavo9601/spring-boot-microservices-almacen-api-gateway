package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.controllers;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.requests.InmuebleServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gateway/inmuebles")
public class InmuebleController {

    @Autowired
    private InmuebleServiceRequest inmuebleServiceRequest;


    @GetMapping
    public ResponseEntity<List<Object>> findAllInmuebles() {
        return new ResponseEntity<>(this.inmuebleServiceRequest.findAllInmuebles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveInmueble(@RequestBody Object inmueble) {
        return new ResponseEntity<>(this.inmuebleServiceRequest.saveInmueble(inmueble), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInmueble(@PathVariable Long id) {
        this.inmuebleServiceRequest.deleteInmueble(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
