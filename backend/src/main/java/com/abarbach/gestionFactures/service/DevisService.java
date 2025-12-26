package com.abarbach.gestionFactures.service;

import com.abarbach.gestionFactures.DTO.DevisDto;
import com.abarbach.gestionFactures.DTO.LigneDevisDto;
import com.abarbach.gestionFactures.entities.*;
import com.abarbach.gestionFactures.repository.ClientRepository;
import com.abarbach.gestionFactures.repository.DevisRepository;
import com.abarbach.gestionFactures.repository.FactureRepository;
import com.abarbach.gestionFactures.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevisService {

    private final DevisRepository devisRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final FactureRepository factureRepository;

    private static final double TAUX_TVA = 0.20; // 20% TVA

    public List<DevisDto> getAllDevis() {
        return devisRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public DevisDto getDevisById(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis non trouvé"));
        return mapToDto(devis);
    }

    @Transactional
    public DevisDto createDevis(DevisDto devisDto) {
        // Vérification unicité numéro
        if (devisRepository.existsByNumeroDevis(devisDto.numeroDevis())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le numéro de devis existe déjà");
        }

        Client client = clientRepository.findById(devisDto.clientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));

        // Création du devis
        Devis devis = Devis.builder()
                .numeroDevis(devisDto.numeroDevis())
                .dateEmission(devisDto.dateEmission())
                .statut(devisDto.statut() != null ? devisDto.statut() : StatutDevis.BROUILLON)
                .client(client)
                .build();

        // Gestion des lignes de produits
        List<LigneDevis> lignes = new ArrayList<>();
        if (devisDto.lignes() != null) {
            for (LigneDevisDto lDto : devisDto.lignes()) {
                Produit produit = produitRepository.findById(lDto.produitId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit ID " + lDto.produitId() + " non trouvé"));

                LigneDevis ligne = LigneDevis.builder()
                        .produit(produit)
                        .quantite(lDto.quantite())
                        .prixUnitaire(produit.getPrix()) // On fige le prix du produit au moment du devis
                        .devis(devis)
                        .build();
                lignes.add(ligne);
            }
        }

        devis.setLignes(lignes);
        Devis savedDevis = devisRepository.save(devis);

        return mapToDto(savedDevis);
    }

    @Transactional
    public void deleteDevis(Long id) {
        Devis foundDevis = devisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis non trouvé"));
        devisRepository.delete(foundDevis);
    }

    @Transactional
    public void transformerEnFacture(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis non trouvé"));

        // 1. Vérifier le statut
        if (devis.getStatut() == StatutDevis.BROUILLON) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de facturer un brouillon. Le devis doit être validé.");
        }

        // 2. Générer le numéro de facture prévu
        String numeroFacture = "FACT-" + devis.getNumeroDevis();

        // 3. Vérifier si elle existe déjà (C'EST LA CORRECTION CRITIQUE)
        if (factureRepository.existsByNumeroFacture(numeroFacture)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Une facture existe déjà pour ce devis.");
        }

        // 4. Création de la facture
        Facture facture = Facture.builder()
                .numeroFacture(numeroFacture)
                .dateFacture(LocalDate.now())
                .client(devis.getClient())
                .devisSource(devis)
                // On recalcule ou on récupère le montant via le DTO pour être sûr
                .montantTTC(mapToDto(devis).totalTTC())
                .build();

        factureRepository.save(facture);
    }

    // Mapping Entity -> DTO avec calculs financiers
    private DevisDto mapToDto(Devis devis) {
        List<LigneDevisDto> lignesDto = devis.getLignes() == null ? List.of() :
                devis.getLignes().stream()
                        .map(l -> new LigneDevisDto(l.getId(), l.getProduit().getId(), l.getQuantite()))
                        .toList();

        // Calcul Total HT
        double totalHT = devis.getLignes() == null ? 0.0 :
                devis.getLignes().stream()
                        .mapToDouble(l -> l.getPrixUnitaire() * l.getQuantite())
                        .sum();

        // Calcul TVA
        double totalTVA = BigDecimal.valueOf(totalHT * TAUX_TVA)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        // Calcul TTC
        double totalTTC = BigDecimal.valueOf(totalHT + totalTVA)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new DevisDto(
                devis.getId(),
                devis.getNumeroDevis(),
                devis.getDateEmission(),
                devis.getStatut(),
                devis.getClient().getId(),
                lignesDto,
                totalHT,
                totalTVA,
                totalTTC
        );
    }
    @Transactional
    public DevisDto validerDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis non trouvé"));

        // On passe le statut à ACCEPTE (ou le statut correspondant dans votre Enum)
        devis.setStatut(StatutDevis.ACCEPTE);

        return mapToDto(devisRepository.save(devis));
    }
}