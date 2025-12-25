package com.abarbach.gestionFactures.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProduitUpdateDto(@NotBlank String nom, @NotBlank String description, @Positive BigDecimal prix, @Min(0) int stock, @NotBlank String categorie) {
}
