package com.example.demo.service;

import com.example.demo.model.Imovel;
import com.example.demo.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository repository;


    public List<Imovel> ListarTodos(){
        return repository.findAll();
    }

    public Imovel salvar(Imovel imovel){
        return repository.save(imovel);
    }
    public List<Imovel> buscarPorCep(String cep){
        return repository.findByCep(cep);
    }
    public Imovel deletar(Long id){
        return repository.findById(id).orElse(null);
    }

    public Imovel atualizarEndereco(Imovel imovelAtualizado, Long id){
        Imovel existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imovél não encontrado"));

        existente.setCep(imovelAtualizado.getCep());
        existente.setId(imovelAtualizado.getId());
        existente.setEndereco(imovelAtualizado.getEndereco());
        existente.setPreco(imovelAtualizado.getPreco());
        existente.setNumeroEnde(imovelAtualizado.getNumeroEnde());
        existente.setTamanho(imovelAtualizado.getTamanho());

        return repository.save(existente);
    }

    //BUSCA UM IMOVEL PELO ID
    public Optional<Imovel> buscarPorId(long id){
        return repository.findById(id);
    }
    //BUSCA IMOVEIS COM MAIS DE UM ARGUMENTO
    public List<Imovel> buscarImoveis(String cep,Float tamanhoMin,Float tamanhoMax,Float precoMin,Float precoMax){
        return repository.buscarImoveis(cep,tamanhoMin,tamanhoMax,precoMin,precoMax);
    }
}
