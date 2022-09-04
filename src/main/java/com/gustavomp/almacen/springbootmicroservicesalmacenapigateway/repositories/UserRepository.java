package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.repositories;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.Role;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // findBy + nombreCampo
    Optional<User> findByUsername(String username);

    @Modifying // Para indicar que es una query de modificación
    @Query("UPDATE User u SET u.role=:role WHERE u.username=:username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);
}
