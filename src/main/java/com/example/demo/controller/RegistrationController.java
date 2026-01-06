package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setNome(dto.getNome());
        u.setImoveis(dto.getImoveis());
        u.setSenha(passwordEncoder.encode(dto.getSenha()));
        u.setRole(dto.getRole());
        System.out.println("Nome:" +  u.getNome());
        repository.save(u);
        return ResponseEntity.ok("Usuário criado");
    }
}
