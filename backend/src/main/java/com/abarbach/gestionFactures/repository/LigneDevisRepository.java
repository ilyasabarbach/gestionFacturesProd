package com.abarbach.gestionFactures.repository;

import com.abarbach.gestionFactures.entities.LigneDevis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneDevisRepository extends JpaRepository<LigneDevis, Long> {
}