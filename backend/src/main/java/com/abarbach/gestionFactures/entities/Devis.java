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
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true,nullable = false)
    private String numeroDevis;
    private LocalDate dateEmission;
    @Enumerated(EnumType.STRING)
    private StatutDevis statut;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
