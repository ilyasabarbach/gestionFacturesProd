package com.abarbach.gestionFactures.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LigneDevisDto(
        Long id,
        @NotNull(message = "L'ID du produit est obligatoire") Long produitId,
        @Min(value = 1, message = "La quantité doit être au moins de 1") int quantite
) {
}