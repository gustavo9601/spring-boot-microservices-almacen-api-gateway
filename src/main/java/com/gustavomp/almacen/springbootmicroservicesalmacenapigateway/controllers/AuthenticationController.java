package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.controllers;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services.AuthenticationService;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/sign-up")
    public ResponseEntity<User> singUp(@RequestBody User user) {
        if(this.userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<User> singIn(@RequestBody User user) {
        logger.info("User => {}", user);
        return new ResponseEntity<>(this.authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
    }

}
