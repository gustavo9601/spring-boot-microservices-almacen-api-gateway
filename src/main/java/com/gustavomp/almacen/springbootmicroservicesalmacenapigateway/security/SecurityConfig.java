package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.Role;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilterBean()  {
        return new JwtAuthorizationFilter();
    }

    // Se setea la configuracion de los paths seran protegidos y cuales no
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(
                AuthenticationManagerBuilder.class
        );

        // Seteando el user detail service
        auth.userDetailsService(this.customUserDetailsService)
                .passwordEncoder(this.passwordEncoder);

        AuthenticationManager authenticationManager = auth.build();

        // Devolviendo las reglas de validacion sobre el api
        return http
                .csrf().disable() // Desactivando el CSRF
                .cors().disable() // Desactivando el CORS
                .formLogin().disable() // Desactivando el form login
                .authorizeRequests()
                .antMatchers("/api/authentication/sign-in", "/api/authentication/sign-up").permitAll() // Permitiendo el acceso a los endpoints de autenticacion
                .antMatchers(HttpMethod.GET, "/gateway/inmuebles").permitAll() // Permitiendo el acceso a los endpoints GET de inmuebles
                .antMatchers("/gateway/inmuebles").hasRole(Role.ADMIN.name()) // Permitiendo el acceso solo si tiene role admin

                .anyRequest().authenticated() // Require AUTHENTICATED role for all requests
                .and()
                .authenticationManager(authenticationManager) // seteando el authentication manager
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// No se creara una session
                .and()
                .addFilterBefore(jwtAuthorizationFilterBean(), UsernamePasswordAuthenticationFilter.class) // Agregando el filtro de autorizacion
                .build();
    }


}
