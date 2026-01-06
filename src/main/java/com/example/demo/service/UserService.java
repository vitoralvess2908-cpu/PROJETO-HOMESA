//3#Contém as regras de negócios.
package com.example.demo.service;

import com.example.demo.dto.UsuarioClienteDto;
import com.example.demo.dto.UsuarioProprietarioDto;
import com.example.demo.model.User;
import com.example.demo.repository.ImovelRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.ImovelDto;
import java.util.List;
@Service
public class UserService {

    //Essa anotação intância o objeto da classe 'UserRepository' automaticamente
    @Autowired
    private UserRepository repository;


    @Autowired
    private ImovelRepository imovelRepository;

    //Depedencia para crypitografar uma senha (spring security)
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> listarTodos() {
        //A variável (É uma lista) 'repository' tem todos os metodos da classe JpaRepository.E tem um metodo 'findAll', que lista todos elementos de uma Lista.
        return repository.findAll();
    }

    public User salvar(User user) {
        //Esse metodo coloca um novo usuário no banco de dados.
        String encoder = this.passwordEncoder.encode(user.getSenha());
        user.setSenha(encoder);
        return repository.save(user);
    }

    public User buscarPorId(Long id) {
        //Esse metodo busca dentro da lista um objeto por ID, e se não exitir retorna NULL
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        //Deleta um objeto por ID
        repository.deleteById(id);
    }

    public User atualizar(Long id, User userAtualizado) {
        User existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String encoder = this.passwordEncoder.encode(existente.getSenha());
        userAtualizado.setSenha(encoder);
        existente.setNome(userAtualizado.getNome());
        existente.setEmail(userAtualizado.getEmail());

        return repository.save(existente);
    }

    public Boolean validarSenha(User usuario) {
        // busca pelo email
        User usuarioBanco = repository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Boolean valid = passwordEncoder.matches(usuario.getSenha(),usuarioBanco.getSenha());
        return valid;
    }

    public UsuarioProprietarioDto buscarDadosProprietario(Long userId){
        User usuario = repository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<ImovelDto> imovel = imovelRepository.
                findByUsuario_Id(userId)
                .stream()
                .map(ImovelDto::new)
                .toList();

        System.out.println("Usuario encontrado:");
        System.out.println("  - ID: " + usuario.getId());
        System.out.println("  - Nome: " + usuario.getNome());
        System.out.println("  - Email: " + usuario.getEmail());
        return new UsuarioProprietarioDto(
                usuario,
                imovel
        );
    }

    public UsuarioClienteDto buscarDadosCliente(Long userId) {
        User usuario = repository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontardo"));
        List<ImovelDto> imovel = imovelRepository.
                findByUsuario_Id(userId)
                .stream()
                .map(ImovelDto::new)
                .toList();

        System.out.println("Usuario encontrado:");
        System.out.println("  - ID: " + usuario.getId());
        System.out.println("  - Nome: " + usuario.getNome());
        System.out.println("  - Email: " + usuario.getEmail());
        return new UsuarioClienteDto(
                usuario,
                imovel
        );
    }
}

