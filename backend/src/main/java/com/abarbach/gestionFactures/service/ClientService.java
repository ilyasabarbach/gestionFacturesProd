package com.abarbach.gestionFactures.service;

import com.abarbach.gestionFactures.DTO.ClientDto;
import com.abarbach.gestionFactures.DTO.ClientUpdateDto;
import com.abarbach.gestionFactures.entities.Client;
import com.abarbach.gestionFactures.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ClientDto getClientById(Long id) {
        Client foundClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));
        return mapToDto(foundClient);
    }

    // NOUVELLE MÉTHODE : Recherche par nom
    public List<ClientDto> searchClients(String nom) {
        return clientRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(this::mapToDto)
                .toList();
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = Client.builder()
                .nom(clientDto.nom())
                .email(clientDto.email())
                .telephone(clientDto.telephone())
                .build();

        Client savedClient = clientRepository.save(client);
        return mapToDto(savedClient);
    }

    public ClientUpdateDto updateClient(ClientUpdateDto clientUpdateDto, Long id) {
        Client foundClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));

        foundClient.setNom(clientUpdateDto.nom());
        foundClient.setEmail(clientUpdateDto.email());
        foundClient.setTelephone(clientUpdateDto.telephone());

        clientRepository.save(foundClient);

        return new ClientUpdateDto(
                foundClient.getNom(),
                foundClient.getEmail(),
                foundClient.getTelephone()
        );
    }

    public void deleteClient(Long id) {
        Client foundClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé"));
        clientRepository.delete(foundClient);
    }

    // Méthode utilitaire pour éviter la duplication de code
    private ClientDto mapToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getNom(),
                client.getEmail(),
                client.getTelephone()
        );
    }
}