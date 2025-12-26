package com.abarbach.gestionFactures.DTO;

import java.time.LocalDate;

public record FactureDto(
        Long id,
        String numeroFacture,
        LocalDate dateFacture,
        double montantTTC,
        String nomClient, // Pour l'affichage facile
        String referenceDevis // Pour savoir de quel devis elle vient
) {
}