package com.fitdashy.fitdashy_backend.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fitdashy.fitdashy_backend.model.ERole;
import com.fitdashy.fitdashy_backend.model.Role;
import com.fitdashy.fitdashy_backend.model.User;
import com.fitdashy.fitdashy_backend.payload.requests.LoginRequest;
import com.fitdashy.fitdashy_backend.payload.requests.RegisterRequest;
import com.fitdashy.fitdashy_backend.payload.responses.ErrorMessageResponse;
import com.fitdashy.fitdashy_backend.payload.responses.MessageResponse;
import com.fitdashy.fitdashy_backend.payload.responses.UserInfoResponse;
import com.fitdashy.fitdashy_backend.repository.RoleRepository;
import com.fitdashy.fitdashy_backend.repository.UserRepository;

import com.fitdashy.fitdashy_backend.security.jwt.JwtUtils;
import com.fitdashy.fitdashy_backend.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Generate the JWT access token cookie to return to the authenticated user
            ResponseCookie accessTokenCookie = jwtUtils.generateAccessTokenCookie(username);

            // Generate the JWT refresh token cookie to return to the authenticated user
            ResponseCookie refreshTokenCookie = jwtUtils.generateRefreshTokenCookie(username);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessageResponse("Invalid username or password", e));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        registerRequest.setUsername(registerRequest.getUsername().toLowerCase());
        registerRequest.setEmail(registerRequest.getEmail().toLowerCase());

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        // Create new user's account
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getFullname(),
                encoder.encode(registerRequest.getPassword()));

//        Set<String> strRoles = registerRequest.getRoles();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    case "paid":
//                        Role paidRole = roleRepository.findByName(ERole.ROLE_PAID_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(paidRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie accessCookie = jwtUtils.getCleanAccessTokenCookie();
        ResponseCookie refreshCookie = jwtUtils.getCleanRefreshTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        // Get tokens
        String accessToken = jwtUtils.getAccessTokenFromRequest(request);
        String refreshToken = jwtUtils.getRefreshTokenFromRequest(request);

        // Check if there are no tokens
        if (accessToken == null && refreshToken == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorMessageResponse("The request contained no tokens, so the request is unauthenticated"));
        }

        try {
            jwtUtils.validateJwtToken(accessToken);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse("The access token is valid, and the request is authenticated"));
        } catch (Exception e) { // Access token is invalid or missing
            try {
                jwtUtils.validateJwtToken(refreshToken);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new MessageResponse("The access token has been refreshed, and request is authenticated"));
            } catch (Exception refreshEx) { // Refresh token is invalid or missing
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorMessageResponse("The access and refresh tokens are missing or invalid, so the request is unauthenticated"));
            }
        }
    }
}