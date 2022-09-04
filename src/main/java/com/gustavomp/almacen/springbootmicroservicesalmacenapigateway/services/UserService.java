package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.Role;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(String username, Role newRole);
}
