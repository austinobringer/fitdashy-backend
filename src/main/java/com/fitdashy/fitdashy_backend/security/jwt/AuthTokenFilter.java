package com.fitdashy.fitdashy_backend.security.jwt;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitdashy.fitdashy_backend.payload.responses.ErrorMessageResponse;
import com.fitdashy.fitdashy_backend.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        logger.error("Filter triggered");

        String accessToken = jwtUtils.getAccessTokenFromRequest(request);
        String refreshToken = jwtUtils.getRefreshTokenFromRequest(request);

        if (accessToken != null || refreshToken != null) {
            try {
                jwtUtils.validateJwtToken(accessToken);

                authenticateRequest(request, accessToken); // Access token is valid, and the request is authenticated

                filterChain.doFilter(request, response); // Continue with the filter chain
                return;
            } catch (Exception e) { // Access token is invalid or missing
                try {
                    jwtUtils.validateJwtToken(refreshToken);

                    // Refresh token is valid, generate a new access token
                    ResponseCookie accessTokenCookie = jwtUtils.refreshAccessToken(refreshToken);

                    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString()); // Add cookie to response

                    authenticateRequest(request, accessTokenCookie.getValue()); // Access token is valid, and the request is authenticated

                    filterChain.doFilter(request, response); // Continue with the filter chain
                } catch (Exception refreshEx) { // Refresh token is invalid or missing
                }
            }
        }

        filterChain.doFilter(request, response); // Continue with the filter chain
    }

    public void authenticateRequest(HttpServletRequest request, String accessToken) {
        String username = jwtUtils.getUsernameFromJwtToken(accessToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}