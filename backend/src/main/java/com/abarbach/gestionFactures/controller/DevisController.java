package com.abarbach.gestionFactures.controller;

import com.abarbach.gestionFactures.DTO.DevisDto;
import com.abarbach.gestionFactures.service.DevisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devis")
@RequiredArgsConstructor
public class DevisController {

    private final DevisService devisService;

    // Récupérer tous les devis
    @GetMapping
    public ResponseEntity<List<DevisDto>> getAllDevis() {
        return ResponseEntity.ok(devisService.getAllDevis());
    }

    // Récupérer un devis par ID
    @GetMapping("/{id}")
    public ResponseEntity<DevisDto> getDevisById(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.getDevisById(id));
    }

    // Créer un nouveau devis
    @PostMapping
    public ResponseEntity<DevisDto> createDevis(@Valid @RequestBody DevisDto devisDto) {
        return new ResponseEntity<>(devisService.createDevis(devisDto), HttpStatus.CREATED);
    }

    // Transformer un devis en facture
    @PostMapping("/{id}/facturer")
    public ResponseEntity<Void> transformerEnFacture(@PathVariable Long id) {
        devisService.transformerEnFacture(id);
        return ResponseEntity.ok().build();
    }

    // Supprimer un devis
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/valider")
    public ResponseEntity<DevisDto> validerDevis(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.validerDevis(id));
    }
}