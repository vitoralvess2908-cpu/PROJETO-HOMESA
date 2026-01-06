package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table (name="Imovel")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private float preco;
    private String endereco;
    private int numeroEnde;
    private String cep;
    private float tamanho;
    private boolean vendido = false;


    @Lob
    private byte[] imagem;

    @ManyToOne(optional = false)//usário é obrigatório
    @NotNull
    @JsonIgnore
    @JoinColumn(name="usuario_id")
    private User usuario;

    @Override
    public String toString() {
        return "Imovel{" +
                "Id=" + Id +
                ", Preco=" + preco +
                ", endereco='" + endereco + '\'' +
                ", numeroEnde=" + numeroEnde +
                ", Cep='" + cep + '\'' +
                ", Tamanho=" + tamanho +
                '}';
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumeroEnde() {
        return numeroEnde;
    }

    public void setNumeroEnde(int numeroEnde) {
        this.numeroEnde = numeroEnde;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public byte[] getImagem() {
    return imagem;
}

    public void setImagem(byte[] imagem) {
    this.imagem = imagem;}

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }


}
