package com.abarbach.gestionFactures.controller;

import com.abarbach.gestionFactures.DTO.ProduitDto;
import com.abarbach.gestionFactures.DTO.ProduitUpdateDto;
import com.abarbach.gestionFactures.repository.ProduitRepository;
import com.abarbach.gestionFactures.service.ProduitService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    }
