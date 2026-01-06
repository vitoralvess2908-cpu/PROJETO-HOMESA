package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImovelRequest (
         float preco,
         String endereco,
         String cep,
         MultipartFile imagem
){}
