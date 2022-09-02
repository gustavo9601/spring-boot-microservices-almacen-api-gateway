package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 150, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @PrePersist
    public void prePersist() {
        this.setFechaCreacion(LocalDateTime.now());
    }

}
