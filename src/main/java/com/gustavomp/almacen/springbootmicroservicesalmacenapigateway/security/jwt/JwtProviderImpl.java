package com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security.jwt;

import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.models.User;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.security.UserPrincipal;
import com.gustavomp.almacen.springbootmicroservicesalmacenapigateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.expiration-in-ms}")
    private long EXPIRATION_TIME;

    @Override
    public String generateToken(UserPrincipal auth) {
        String authoritiesString = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Formato del token

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authoritiesString) // añadiendo propiedad al token
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRole().name()) // añadiendo propiedad al token
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token == null) {
            return null;
        }
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = this.getClaims(request);
        if (claims == null) {
            return null;
        }

        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        List<GrantedAuthority> authorityList = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toList());

        UserDetails userDetails = UserPrincipal.builder()
                .id(userId)
                .username(username)
                .authorities(authorityList)
                .build();

        if (username == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = this.getClaims(request);
        // Si no tiene los datos desencriptados en el token
        if (claims == null) {
            return false;
        }
        // Si ya expiró
        if (claims.getExpiration().before(new Date())) {
            return false;
        }
        return true;
    }

}
