package com.example.crudspringboot.filter;

import com.example.crudspringboot.configuration.JwtUtils;
import com.example.crudspringboot.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    // Cette classe crée un filtre HTTP pour Spring Security.
    // Son rôle : vérifier si chaque requête contient un JWT valide et authentifier l’utilisateur si oui.

    private final CustomUserDetailsService customUserDetailsService;
    // Service qui charge un utilisateur depuis la base de données par son username.

    private final JwtUtils jwtUtils;
    // Utilitaire pour manipuler les JWT : extraire le username, vérifier si le token est valide.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1️⃣ Récupérer le header Authorization de la requête
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2️⃣ Vérifier que le header n’est pas nul et commence par "Bearer "
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // 2a. Extraire le token JWT (on enlève "Bearer ")
            jwt = authHeader.substring(7);

            // 2b. Extraire le username du token
            username = jwtUtils.extractUsername(jwt);
        }
        // 3️⃣ Vérifier que l’utilisateur n’est pas déjà authentifié dans le contexte Spring
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3a. Charger l’utilisateur depuis la base de données
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // 3b. Vérifier que le token est valide pour cet utilisateur
            if(jwtUtils.validateToken(jwt, userDetails)) {

                // 3c. Créer un objet d’authentification Spring Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                // principal = l’utilisateur
                                userDetails,
                                null,
                                userDetails.getAuthorities() // rôles/permissions
                        );
                // 3d. Lier les détails de la requête à l’objet d’authentification
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 3e. Mettre l’utilisateur authentifié dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // 4️⃣ Continuer la chaîne de filtres, même si pas d’authentification
        filterChain.doFilter(request, response);
    }
}
