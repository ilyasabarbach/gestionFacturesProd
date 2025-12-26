package com.abarbach.gestionFactures.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroFacture;

    private LocalDate dateFacture;

    // Lien vers le devis d'origine
    @OneToOne
    @JoinColumn(name = "devis_id")
    private Devis devisSource;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private double montantTTC;
}