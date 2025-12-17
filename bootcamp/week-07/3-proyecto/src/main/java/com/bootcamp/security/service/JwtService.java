package com.bootcamp.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // ========== GENERACIÓN DE TOKENS ==========

    /**
     * Genera un access token para el usuario autenticado.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un access token con claims adicionales.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Genera un refresh token.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Construye el token JWT con los claims especificados.
     */
    private String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails,
                             long expiration) {
        // TODO: Implementar construcción del token
        // 1. Extraer roles del userDetails y agregarlos a extraClaims
        //    - Usar getAuthorities() y convertir a String separado por comas
        //    - Agregar con clave "roles"
        // 2. Usar Jwts.builder() para construir:
        //    - .claims(extraClaims)
        //    - .subject(userDetails.getUsername())
        //    - .issuedAt(new Date(System.currentTimeMillis()))
        //    - .expiration(new Date(System.currentTimeMillis() + expiration))
        //    - .signWith(getSigningKey(), Jwts.SIG.HS256)
        //    - .compact()

        return null; // TODO: Reemplazar con implementación
    }

    // ========== VALIDACIÓN DE TOKENS ==========

    /**
     * Valida si el token es válido para el usuario dado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // TODO: Implementar validación
        // 1. Extraer username del token
        // 2. Verificar que coincida con userDetails.getUsername()
        // 3. Verificar que el token no haya expirado
        // 4. Retornar true solo si ambas condiciones se cumplen

        return false; // TODO: Reemplazar
    }

    /**
     * Verifica si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        // TODO: Comparar fecha de expiración con fecha actual
        return false; // TODO: Reemplazar
    }

    // ========== EXTRACCIÓN DE CLAIMS ==========

    /**
     * Extrae el username (subject) del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae los roles del token.
     */
    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }

    /**
     * Extrae un claim específico usando una función resolver.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token.
     */
    private Claims extractAllClaims(String token) {
        // TODO: Implementar extracción de claims
        // Usar Jwts.parser()
        //     .verifyWith(getSigningKey())
        //     .build()
        //     .parseSignedClaims(token)
        //     .getPayload()

        return null; // TODO: Reemplazar
    }

    // ========== UTILIDADES ==========

    /**
     * Obtiene la clave de firma a partir del secreto configurado.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el tiempo de expiración del access token.
     */
    public long getJwtExpiration() {
        return jwtExpiration;
    }
}
