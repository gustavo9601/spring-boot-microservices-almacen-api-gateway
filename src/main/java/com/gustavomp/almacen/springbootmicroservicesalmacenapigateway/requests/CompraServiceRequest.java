package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.requests;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        value = "compras-service",
        path = "/api/compras",
   //    url = "${compras.service.url}", // no se necesita la url ya que eureka resuelve por el dominio
        configuration = FeignConfiguration.class
)
public interface CompraServiceRequest {

        @GetMapping("/{id}")
        List<Object> getAllComprasByUserId(@PathVariable(value = "id") Long id);

        @PostMapping
        Object saveCompra(@RequestBody Object compra);

}
