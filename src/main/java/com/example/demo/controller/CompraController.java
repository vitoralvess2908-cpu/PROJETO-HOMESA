package com.example.demo.controller;

import com.example.demo.model.Compra;
import com.example.demo.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping ("/imoveis")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/compra/{idUsuario}/{idImovel}")
    public ResponseEntity<Compra> comprar(@PathVariable Long idUsuario,
                                          @PathVariable Long idImovel){
        Compra compra = compraService.comprarImovel(idUsuario,idImovel);
        return ResponseEntity.ok(compra);
    }
}
