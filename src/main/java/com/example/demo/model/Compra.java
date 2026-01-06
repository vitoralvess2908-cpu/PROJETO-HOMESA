package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User comprador;

    @ManyToOne
    private Imovel imovel;

    private LocalDateTime dataCompra;

    private float precoValor;

    public float getPrecoValor() {
        return precoValor;
    }

    public void setPrecoValor(float precoValor) {
        this.precoValor = precoValor;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public User getComprador() {
        return comprador;
    }

    public void setComprador(User comprador) {
        this.comprador = comprador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
