package com.abarbach.gestionFactures.service;

import com.abarbach.gestionFactures.DTO.ProduitDto;
import com.abarbach.gestionFactures.DTO.ProduitUpdateDto;
import com.abarbach.gestionFactures.entities.Produit;
import com.abarbach.gestionFactures.repository.ProduitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    public List<ProduitDto> getAllProducts() {
        return produitRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ProduitDto getProductById(Long id) {
        Produit foundProduct = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        return mapToDto(foundProduct);
    }

    public ProduitDto createProduct(ProduitDto produitDto) {
        Produit product = Produit.builder()
                .nom(produitDto.nom())
                .description(produitDto.description())
                .prix(produitDto.prix())
                .quantiteStock(produitDto.quantiteStock()) // CORRIGÉ : quantiteStock() au lieu de stock()
                .categorie(produitDto.categorie())
                .build();

        Produit savedProduct = produitRepository.save(product);
        return mapToDto(savedProduct);
    }

    public ProduitUpdateDto updateProduct(ProduitUpdateDto produitDto, Long id) {
        Produit foundProduct = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));

        foundProduct.setNom(produitDto.nom());
        foundProduct.setDescription(produitDto.description());
        foundProduct.setPrix(produitDto.prix());
        foundProduct.setQuantiteStock(produitDto.quantiteStock()); // CORRIGÉ
        foundProduct.setCategorie(produitDto.categorie());

        produitRepository.save(foundProduct);

        return new ProduitUpdateDto(
                foundProduct.getNom(),
                foundProduct.getDescription(),
                foundProduct.getPrix(),
                foundProduct.getQuantiteStock(), // CORRIGÉ
                foundProduct.getCategorie()
        );
    }

    public void deleteProduct(Long id) {
        Produit foundProduct = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        produitRepository.delete(foundProduct);
    }

    // Méthode utilitaire pour mapper Entity -> DTO
    private ProduitDto mapToDto(Produit p) {
        return new ProduitDto(
                p.getId(),
                p.getNom(),
                p.getDescription(),
                p.getPrix(),
                p.getQuantiteStock(), // CORRIGÉ
                p.getCategorie()
        );
    }
    @Transactional
    public ProduitDto ajusterStock(Long id, int quantite, boolean estAjout) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));

        int nouveauStock;
        if (estAjout) {
            nouveauStock = produit.getQuantiteStock() + quantite;
        } else {
            nouveauStock = produit.getQuantiteStock() - quantite;
            if (nouveauStock < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuffisant pour décrémenter cette quantité");
            }
        }

        produit.setQuantiteStock(nouveauStock);
        return mapToDto(produitRepository.save(produit));
    }
}