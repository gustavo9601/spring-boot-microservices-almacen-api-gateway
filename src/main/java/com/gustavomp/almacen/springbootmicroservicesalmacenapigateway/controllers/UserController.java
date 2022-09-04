package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.controllers;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.Role;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security.UserPrincipal;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change/{role}")
    public ResponseEntity<Void> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable(value = "role") Role role) {

        this.userService.changeRole(userPrincipal.getUsername(), role);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // AuthenticationPrincipal // se accede al usuario autenticado
    @GetMapping
    public ResponseEntity<User> getCurrentUserToken(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(this.userService.findByUsernameReturnToken(userPrincipal.getUsername()), HttpStatus.OK);
    }


}
