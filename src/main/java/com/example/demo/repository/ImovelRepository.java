package com.example.demo.repository;

import com.example.demo.model.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ImovelRepository extends JpaRepository<Imovel,Long> {

    List<Imovel> findByCep(String cep);

    Optional<Imovel> findById(Long id);

    @Query("SELECT i FROM Imovel i WHERE " +
            "(:cep IS NULL OR i.cep = :cep) AND " +
            "(:tamanhoMin IS NULL OR i.tamanho >= :tamanhoMin) AND " +
            "(:tamanhoMax IS NULL OR i.tamanho <= :tamanhoMax) AND" +
            "(:precoMin IS NULL OR i.preco >= :precoMin) AND " +
            "(:precoMax IS NULL OR i.preco <= :precoMax)")
    List<Imovel> buscarImoveis(@Param("cep") String cep,
                               @Param("tamanhoMin") Float tamanhoMin,
                               @Param("tamanhoMax") Float tamanhoMax,
                               @Param("precoMin") Float precoMin,
                               @Param("precoMax") Float precoMax);

    List<Imovel> findByUsuario_Id(Long proprietarioId);
}