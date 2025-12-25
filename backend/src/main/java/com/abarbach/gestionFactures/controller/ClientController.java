package com.abarbach.gestionFactures.controller;

import com.abarbach.gestionFactures.DTO.ClientDto;
import com.abarbach.gestionFactures.DTO.ClientUpdateDto;
import com.abarbach.gestionFactures.entities.Client;
import com.abarbach.gestionFactures.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> getAllClients(){
        return clientService.getAllClients();
    }
    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }

        @PostMapping
    public ClientDto createClient(@RequestBody @Valid ClientDto clientDto){
        return clientService.createClient(clientDto);
    }

    @PutMapping("/{id}")
    public ClientUpdateDto updateClient(@RequestBody @Valid ClientUpdateDto clientUpdateDto, @PathVariable Long id){
      return clientService.updateClient(clientUpdateDto,id);
    }
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }

}
