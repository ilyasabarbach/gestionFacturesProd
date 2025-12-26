package com.abarbach.gestionFactures.DTO;

import com.abarbach.gestionFactures.entities.Role;

public record RegisterDto(String email, String password, Role role) {
}