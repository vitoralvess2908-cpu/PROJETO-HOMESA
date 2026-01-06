//4#É o ponto de entradada da Api.Ele recebe as requisições HTTP
package com.example.demo.controller;

import com.example.demo.configuration.UserPrincipal;
import com.example.demo.dto.UsuarioClienteDto;
import com.example.demo.dto.UsuarioProprietarioDto;
import com.example.demo.model.User ;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*") // ou coloque seu IP/porta exato aqui
//PERMITE QUE ESTA CLASSE RECEBA REQUISIÇÕES HTTP E TRANSFORME EM OBJETOS Json
@RestController
//Define o caminho base para todos os metodos dessa classe. (usuarios).
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    public List<User> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<User> criar(@RequestBody User user) {
        if(repository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body(user);
        }
        System.out.println("Dados recebidos no POST:");
        System.out.println(user);
        service.salvar(user);
        return  ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public User buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }


    @GetMapping("/meProprietario")
    public UsuarioProprietarioDto meProprietario(@AuthenticationPrincipal UserPrincipal principal){
        return service.buscarDadosProprietario(principal.getId());
    }

    @GetMapping("/meCliente")
    public UsuarioClienteDto meCliente(@AuthenticationPrincipal UserPrincipal principal){
        return service.buscarDadosCliente(principal.getId());
    }


    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @PutMapping("/{id}")
    public User atualizar(@PathVariable Long id, @RequestBody User useratualizado){
        System.out.println("Usário antingo: "+service.buscarPorId(id)+"\nUsuário atualizado: " + useratualizado);
        return service.atualizar(id,useratualizado);
    }


        @PostMapping("/login")
        public ResponseEntity<User> validarSenha(@RequestBody User usuario){
            Boolean valid = service.validarSenha(usuario);
            if(!valid){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }else{
                return ResponseEntity.status(200).build();
            }
        }
}