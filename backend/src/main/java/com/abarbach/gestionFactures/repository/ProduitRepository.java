package com.abarbach.gestionFactures.repository;

import com.abarbach.gestionFactures.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

}
