package com.ispace.sensoraudit.filter;

import com.ispace.sensoraudit.dto.UserResponse;
import com.ispace.sensoraudit.service.UserService;
import com.ispace.sensoraudit.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public TokenAuthFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromHeader(request);

        if (StringUtils.hasText(token)) {
            String email = jwtUtils.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserResponse userDetails = userService.loadUserByUsername(email);

                if (jwtUtils.isTokenValid(token, userDetails.getUserName())) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("USER"))
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) {
            return authHeader.substring(7);
        }
        return null;
    }
}
