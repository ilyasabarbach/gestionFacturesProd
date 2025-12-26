package com.abarbach.gestionFactures.service;

import com.abarbach.gestionFactures.DTO.FactureDto;
import com.abarbach.gestionFactures.entities.Facture;
import com.abarbach.gestionFactures.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;

    public List<FactureDto> getAllFactures() {
        return factureRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    private FactureDto mapToDto(Facture f) {
        return new FactureDto(
                f.getId(),
                f.getNumeroFacture(),
                f.getDateFacture(),
                f.getMontantTTC(),
                f.getClient().getNom(),
                f.getDevisSource() != null ? f.getDevisSource().getNumeroDevis() : "N/A"
        );
    }
}