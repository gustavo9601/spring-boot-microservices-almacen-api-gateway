package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;

public interface AuthenticationService {
    User signInAndReturnJWT(User user);
}
