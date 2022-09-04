package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.controllers;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.requests.CompraServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gateway/compras")
public class CompraController {

    @Autowired
    private CompraServiceRequest compraServiceRequest;

    @GetMapping("/{id}")
    public ResponseEntity<List<Object>> getAllComprasByUserId(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(this.compraServiceRequest.getAllComprasByUserId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveCompra(@RequestBody Object compra) {
        return new ResponseEntity<>(this.compraServiceRequest.saveCompra(compra), HttpStatus.CREATED);
    }


}
