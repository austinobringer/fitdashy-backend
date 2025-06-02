package com.fitdashy.fitdashy_backend.security.jwt;

import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    @Value("${fitdashy.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fitdashy.app.accessTokenExpirationMs}")
    private int accessTokenExpirationMs;

    @Value("${fitdashy.app.refreshTokenExpirationMs}")
    private int refreshTokenExpirationMs;

    final private String accessCookieName = "accessToken";
    final private String refreshCookieName = "refreshToken";

    /* Retrieve a JWT token from a request */

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return getJwtTokenFromRequest(request, accessCookieName);
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        return getJwtTokenFromRequest(request, refreshCookieName);
    }

    public String getJwtTokenFromRequest(HttpServletRequest request, String requestName) {
        Cookie cookie = WebUtils.getCookie(request, requestName);
        return (cookie != null) ? cookie.getValue() : null;
    }

    /* Generate a cookie containing a JWT token */

    public ResponseCookie generateAccessTokenCookie(String username) {
        return generateJwtTokenCookie(username, accessCookieName, accessTokenExpirationMs);
    }

    public ResponseCookie generateRefreshTokenCookie(String username) {
        return generateJwtTokenCookie(username, refreshCookieName, refreshTokenExpirationMs);
    }

    public ResponseCookie generateJwtTokenCookie(String username, String cookieName, int tokenExpirationMs) {
        String jwt = generateTokenFromUsername(username, tokenExpirationMs);
        return ResponseCookie.from(cookieName, jwt)
                .path("/")
                .sameSite("None")
                .maxAge(tokenExpirationMs / 1000)
                .httpOnly(true)
                .secure(true)
                .build();
    }

    /* Get a clean JWT token which will expire in 0 seconds */

    public ResponseCookie getCleanAccessTokenCookie() {
        return getCleanJwtTokenCookie(accessCookieName);
    }

    public ResponseCookie getCleanRefreshTokenCookie() {
        return getCleanJwtTokenCookie(refreshCookieName);
    }

    public ResponseCookie getCleanJwtTokenCookie(String cookieName) {
        return ResponseCookie.from(cookieName, null)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .build();
    }

    // Refresh the access token with the refresh token
    public ResponseCookie refreshAccessToken(String refreshToken) {
        String username = getUsernameFromJwtToken(refreshToken);
        return generateAccessTokenCookie(username);
    }

    // Validate a JWT token
    public boolean validateJwtToken(String authToken) {
        getParseBuilderForJWTToken(authToken).parse(authToken);
        return true;
    }

    // Retrieve a username from a JWT token
    public String getUsernameFromJwtToken(String token) {
        return getClaimsFromJWTToken(token).getSubject();
    }

    // Retrieve the claims from a JWT token
    public Claims getClaimsFromJWTToken(String token) {
        try {
            return getParseBuilderForJWTToken(token)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException("Parsing claims from JWT token failed: " + e.getMessage(), e);
        }
    }

    // Retrieve the parse builder for a JWT token
    public JwtParser getParseBuilderForJWTToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build();
    }

    // Return the signing key for JWT tokens
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Create a token from a given username
    public String generateTokenFromUsername(String username, int tokenExpirationMs) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}