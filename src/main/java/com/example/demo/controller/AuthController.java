package com.example.demo.controller;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;


    public AuthController(AuthenticationManager authManager, JWTService jwtService, CustomUserDetailsService uds) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = uds;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
            UserDetails ud = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(ud);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    // DTOs simples:
    public static record AuthRequest(String email, String senha) {}
    public static record AuthResponse(String token) {}
}

