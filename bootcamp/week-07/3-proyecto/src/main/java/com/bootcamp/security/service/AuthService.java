package com.bootcamp.security.service;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.exception.DuplicateResourceException;
import com.bootcamp.security.exception.InvalidTokenException;
import com.bootcamp.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Servicio de autenticación: registro, login y refresh tokens.
 */
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Registra un nuevo usuario.
     */
    public UserDTO register(RegisterRequest request) {
        // TODO: Implementar registro
        // 1. Verificar que no exista el username (userRepository.existsByUsername)
        //    - Si existe, lanzar DuplicateResourceException("El username ya está en uso")
        // 2. Verificar que no exista el email (userRepository.existsByEmail)
        //    - Si existe, lanzar DuplicateResourceException("El email ya está registrado")
        // 3. Crear nuevo User:
        //    - setUsername(request.getUsername())
        //    - setEmail(request.getEmail())
        //    - setPassword(passwordEncoder.encode(request.getPassword()))
        //    - setRole(Role.USER)
        // 4. Guardar en repository
        // 5. Convertir a DTO y retornar

        return null; // TODO: Reemplazar
    }

    /**
     * Autentica un usuario y retorna tokens JWT.
     */
    public AuthResponse login(LoginRequest request) {
        // TODO: Implementar login
        // 1. Autenticar con authenticationManager.authenticate()
        //    - Crear UsernamePasswordAuthenticationToken(username, password)
        // 2. Obtener UserDetails del Authentication.getPrincipal()
        // 3. Generar accessToken con jwtService.generateToken(authentication)
        // 4. Generar refreshToken con jwtService.generateRefreshToken(userDetails)
        // 5. Retornar AuthResponse con ambos tokens y expiración

        return null; // TODO: Reemplazar
    }

    /**
     * Genera un nuevo access token usando el refresh token.
     */
    public AuthResponse refreshToken(String refreshToken) {
        // TODO: Implementar refresh
        // 1. Extraer username del refreshToken con jwtService.extractUsername()
        // 2. Cargar userDetails con userDetailsService.loadUserByUsername()
        // 3. Validar refreshToken con jwtService.isTokenValid()
        //    - Si no es válido, lanzar InvalidTokenException
        // 4. Generar nuevo accessToken
        // 5. Retornar AuthResponse (mantener mismo refreshToken)

        return null; // TODO: Reemplazar
    }

    /**
     * Convierte User a UserDTO.
     */
    private UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}
