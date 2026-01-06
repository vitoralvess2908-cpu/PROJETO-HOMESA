package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Imovel;
import com.example.demo.model.Compra;
import com.example.demo.repository.CompraRepository;
import com.example.demo.repository.ImovelRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompraService {
    @Autowired
    ImovelRepository imovelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompraRepository compraRepository;

    public Compra comprarImovel(Long idUsuario, Long idImovel){
        User user = userRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Imovel imovel = imovelRepository.findById(idImovel).orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        if(imovel.isVendido()){
            throw new RuntimeException("Imóvel já foi vendido");
        }

        imovel.setVendido(true);
        imovelRepository.save(imovel);
        Compra compra = new Compra();
        compra.setImovel(imovel);
        compra.setComprador(user);
        compra.setPrecoValor(imovel.getPreco());
        compra.setDataCompra(LocalDateTime.now());
        System.out.println("Compra realizada com sucesso \nDADOS DA COMPRA\nNome do Comprador: " + user + "\nImóvel vendido: " + imovel);
        return compraRepository.save(compra);

    }
}
