package com.abarbach.gestionFactures.repository;

import com.abarbach.gestionFactures.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    boolean existsByNumeroFacture(String numeroFacture);
}