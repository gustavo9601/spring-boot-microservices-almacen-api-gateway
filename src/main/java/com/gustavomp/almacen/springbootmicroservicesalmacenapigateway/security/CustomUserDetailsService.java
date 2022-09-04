package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.services.UserService;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Capturando los roles del usuario
        List<GrantedAuthority> authorities = List.of(SecurityUtils.convertToAuthority(user.getRole().name()));

        // Retornamos el userPrincipal, con los parametros seteados
        return UserPrincipal.builder()
                .user(user)
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
