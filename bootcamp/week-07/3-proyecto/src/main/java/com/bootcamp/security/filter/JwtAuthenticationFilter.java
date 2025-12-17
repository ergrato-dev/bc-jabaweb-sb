package com.bootcamp.security.filter;

import com.bootcamp.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que intercepta cada request y valida el JWT.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // TODO: Implementar filtro JWT
        // 1. Obtener header "Authorization"
        // 2. Si es null o no empieza con "Bearer ", continuar con filterChain
        // 3. Extraer el token (quitar "Bearer ")
        // 4. En un try-catch:
        //    a. Extraer username del token con jwtService
        //    b. Si username != null Y no hay autenticación en SecurityContext:
        //       - Cargar userDetails con userDetailsService
        //       - Validar token con jwtService.isTokenValid()
        //       - Si es válido:
        //         * Crear UsernamePasswordAuthenticationToken
        //         * Establecer details con WebAuthenticationDetailsSource
        //         * Establecer autenticación en SecurityContextHolder
        // 5. Continuar con filterChain.doFilter()

        filterChain.doFilter(request, response);
    }
}
