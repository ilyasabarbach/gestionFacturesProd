package com.abarbach.gestionFactures.repository;

import com.abarbach.gestionFactures.entities.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    boolean existsByNumeroDevis(String numeroDevis);
}