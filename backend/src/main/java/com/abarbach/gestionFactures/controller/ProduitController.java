package com.abarbach.gestionFactures.controller;

import com.abarbach.gestionFactures.DTO.ProduitDto;
import com.abarbach.gestionFactures.DTO.ProduitUpdateDto;
import com.abarbach.gestionFactures.repository.ProduitRepository;
import com.abarbach.gestionFactures.service.ProduitService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {
    private final ProduitService produitService;

    @GetMapping
    public List<ProduitDto> getAllProducts(){
        return produitService.getAllProducts();

    }
    @GetMapping("/{id}")
    public ProduitDto getProductById(@PathVariable Long id){
        return produitService.getProductById(id);
    }
    @PostMapping
    public ProduitDto createProduct(@RequestBody @Valid ProduitDto produitDto){
        return produitService.createProduct(produitDto);
    }
    @PutMapping("/{id}")
    public ProduitUpdateDto updateProduct(@RequestBody @Valid ProduitUpdateDto produitUpdateDto, @PathVariable Long id){
        return produitService.updateProduct(produitUpdateDto,id);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        produitService.deleteProduct(id);
    }
    // PATCH /api/produits/1/stock/ajouter?quantite=5
    @PatchMapping("/{id}/stock/ajouter")
    public ResponseEntity<ProduitDto> ajouterStock(@PathVariable Long id, @RequestParam int quantite) {
        return ResponseEntity.ok(produitService.ajusterStock(id, quantite, true));
    }

    // PATCH /api/produits/1/stock/reduire?quantite=2
    @PatchMapping("/{id}/stock/reduire")
    public ResponseEntity<ProduitDto> reduireStock(@PathVariable Long id, @RequestParam int quantite) {
        return ResponseEntity.ok(produitService.ajusterStock(id, quantite, false));
    }

    }
