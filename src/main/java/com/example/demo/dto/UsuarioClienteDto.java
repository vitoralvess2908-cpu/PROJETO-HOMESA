package com.example.demo.dto;

import com.example.demo.model.User;

import java.util.List;

public class UsuarioClienteDto {

    private Long id;
    private String nome;
    private String email;
    private List<ImovelDto> imoveis;

    public UsuarioClienteDto(User usuario, List<ImovelDto> imoveis) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.imoveis = imoveis;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<ImovelDto> getImoveis() {
        return imoveis;
    }
}
