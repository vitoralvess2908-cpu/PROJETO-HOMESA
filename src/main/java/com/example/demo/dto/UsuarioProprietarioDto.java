package com.example.demo.dto;

import com.example.demo.dto.ImovelDto;
import com.example.demo.model.User;

import java.util.List;

public class UsuarioProprietarioDto {

    private Long id;
    private String nome;
    private String email;
    private List<ImovelDto> imoveis;

    public UsuarioProprietarioDto(User usuario, List<ImovelDto> imoveis) {
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
