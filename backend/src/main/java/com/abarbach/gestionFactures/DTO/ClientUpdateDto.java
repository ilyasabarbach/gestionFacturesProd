package com.abarbach.gestionFactures.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientUpdateDto(@NotBlank String nom, @Email String email,@NotBlank String telephone) {
}
