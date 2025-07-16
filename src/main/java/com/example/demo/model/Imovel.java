package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name="Imóvel")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private float Preco;
    private String endereco;
    private int numeroEnde;
    private String Cep;
    private Double Tamanho;

    @Override
    public String toString() {
        return "Imovel{" +
                "Id=" + Id +
                ", Preco=" + Preco +
                ", endereco='" + endereco + '\'' +
                ", numeroEnde=" + numeroEnde +
                ", Cep='" + Cep + '\'' +
                ", Tamanho=" + Tamanho +
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
        return Preco;
    }

    public void setPreco(float preco) {
        Preco = preco;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public Double getTamanho() {
        return Tamanho;
    }

    public void setTamanho(Double tamanho) {
        Tamanho = tamanho;
    }
}
