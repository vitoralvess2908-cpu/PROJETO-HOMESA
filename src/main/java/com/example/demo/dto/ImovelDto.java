package com.example.demo.dto;

import com.example.demo.model.Imovel;

public class ImovelDto {
    private Long id;
    private String cep;
    private float preco;
    private int numero;
    private byte[] imagem;
    private boolean vendido;

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public float getPreco() {
        return preco;
    }

    public int getNumero() {
        return numero;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public boolean isVendido() {
        return vendido;
    }

    public ImovelDto(Imovel imovel) {
        this.id = imovel.getId();
        this.cep = imovel.getCep();
        this.preco = imovel.getPreco();
        this.numero = imovel.getNumeroEnde();
        this.imagem = imovel.getImagem();
        this.vendido = imovel.isVendido();
    }
}
