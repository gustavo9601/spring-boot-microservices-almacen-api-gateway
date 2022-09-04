package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.Role;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.repositories.UserRepository;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User userCreated = this.userRepository.save(user);

        String tokenGenerated = this.jwtProvider.generateToken(userCreated);
        userCreated.setToken(tokenGenerated);

        return userCreated;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Transactional()
    @Override
    public void changeRole(String username, Role newRole) {
        this.userRepository.updateUserRole(username, newRole);
    }

}

