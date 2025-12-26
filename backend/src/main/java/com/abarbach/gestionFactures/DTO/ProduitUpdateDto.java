package com.abarbach.gestionFactures.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProduitUpdateDto(
        @NotBlank(message = "Le nom est obligatoire") String nom,
        String description,
        @Min(value = 0, message = "Le prix doit être positif") double prix,
        @Min(value = 0, message = "Le stock ne peut pas être négatif") int quantiteStock,
        @NotBlank(message = "La catégorie est obligatoire") String categorie
) {
}