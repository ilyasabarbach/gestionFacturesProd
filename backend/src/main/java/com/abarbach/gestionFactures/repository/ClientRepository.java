package com.abarbach.gestionFactures.repository;

import com.abarbach.gestionFactures.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
