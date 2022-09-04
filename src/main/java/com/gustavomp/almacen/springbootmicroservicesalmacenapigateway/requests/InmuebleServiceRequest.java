package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.requests;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "inmueble-service", //  nombre del recurso
        path = "/api/inmuebles", // path del recurso
        // url = "${inmueble.service.url}", // no se necesita la url ya que eureka resuelve por el dominio
        configuration = FeignConfiguration.class // clase que contiene la configuracion
)
public interface InmuebleServiceRequest {

    /*
     * Mapeo de los metodos que se van a consumir del microservicio
     * */

    @GetMapping
    List<Object> findAllInmuebles();

    @PostMapping
    Object saveInmueble(@RequestBody Object inmueble);

    @DeleteMapping("/{id}")
    Void deleteInmueble(@PathVariable(value = "id") Long id);

}
