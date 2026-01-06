package com.example.demo.controller;

import com.example.demo.model.Imovel;
import com.example.demo.model.User;
import com.example.demo.repository.ImovelRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    @Autowired
    private ImovelService service;
    @Autowired
    private ImovelRepository imovelrepository;
    @Autowired
    private UserRepository userrepository;

    //Acessa o objeto JSON
    @GetMapping
    public List<Imovel> listarImoveis(){
        return service.ListarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Imovel> buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    //Acessa a imagem do imóvel
    @GetMapping(value = "{id}/imagem", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] imagem(@PathVariable long id){
        Imovel imovel = imovelrepository.findById(id).orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        return imovel.getImagem(); // retorna os bytes da image
    }

    @PostMapping("/cadastroImovel")
    public String cadastrarImovel(
            @RequestPart("imovel") Imovel imovel,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            Authentication authentication) throws IOException {

        // ✅ 1. Obtém o nome do usuário logado (username do token JWT)
        String username = authentication.getName();

        // ✅ 2. Busca o usuário no banco
        User usuarioLogado = userrepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // ✅ 3. Verifica se é PROPRIETARIO
        boolean isProprietario = usuarioLogado.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_PROPRIETARIO"));

        // ✅ Vincula o imóvel ao usuário
        imovel.setUsuario(usuarioLogado);

        // ✅ Processa a imagem
        if (imagem != null && !imagem.isEmpty()) {
            imovel.setImagem(imagem.getBytes());
        }

        // ✅ Salva o imóvel
        service.salvar(imovel);

        return "Imóvel cadastrado com sucesso!";
    }
    @GetMapping("/pesquisa")
    public List<Imovel> buscarPorCep(@RequestParam(required = false) String cep,
                                    @RequestParam(required = false) Float tamanhoMin,
                                     @RequestParam(required = false) Float tamanhoMax,
                                    @RequestParam(required = false) Float precoMin,
                                     @RequestParam(required = false) Float precoMax){
        return service.buscarImoveis(cep,tamanhoMin,tamanhoMax,precoMin,precoMax);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }

    @PutMapping("/{id}")
    public Imovel atualizar(@RequestBody Imovel imovelAtualizado, @PathVariable Long id){
        return service.atualizarEndereco(imovelAtualizado,id);
    }
}
