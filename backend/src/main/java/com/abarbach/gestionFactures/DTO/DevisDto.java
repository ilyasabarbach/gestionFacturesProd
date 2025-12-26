package com.abarbach.gestionFactures.DTO;

import com.abarbach.gestionFactures.entities.StatutDevis;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record DevisDto(
        Long id,
        @NotBlank String numeroDevis,
        LocalDate dateEmission,
        StatutDevis statut,
        @NotNull Long clientId,
        List<LigneDevisDto> lignes,
        double totalHT,   // Existant
        double totalTVA,  // AJOUTÉ
        double totalTTC   // AJOUTÉ
) {
}