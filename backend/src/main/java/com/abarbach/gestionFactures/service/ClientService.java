package com.abarbach.gestionFactures.service;

import com.abarbach.gestionFactures.DTO.ClientDto;
import com.abarbach.gestionFactures.DTO.ClientUpdateDto;
import com.abarbach.gestionFactures.entities.Client;
import com.abarbach.gestionFactures.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientDto> getAllClients(){
        return clientRepository.findAll().stream().map(c-> new ClientDto(
                c.getId(),
                c.getNom(),
                c.getEmail(),
                c.getTelephone())
        ).toList();
    }
    public ClientDto createClient(ClientDto clientDto){
        Client client = Client.builder()
                .nom(clientDto.nom())
                .email(clientDto.email())
                .telephone(clientDto.telephone())
                .build();

        Client savedClient =  clientRepository.save(client);
        return new ClientDto(
                savedClient.getId(),
                savedClient.getNom(),
                savedClient.getEmail(),
                savedClient.getTelephone()
                );



    }

    public ClientDto getClientById(Long id){
        Client foundClient = clientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Client non trouvé"));
        return new ClientDto(
                foundClient.getId(),
                foundClient.getNom(),
                foundClient.getEmail(),
                foundClient.getTelephone());
    }

    public ClientUpdateDto updateClient(ClientUpdateDto clientUpdateDto, Long id) {
        Client foundClient = clientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Client non trouvé"));
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
    public void deleteClient(Long id){
        Client foundClient = clientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Client non trouvé"));
        clientRepository.delete(foundClient);
    }
}
