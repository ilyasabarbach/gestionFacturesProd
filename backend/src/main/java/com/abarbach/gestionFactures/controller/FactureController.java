package com.abarbach.gestionFactures.controller;

import com.abarbach.gestionFactures.DTO.FactureDto;
import com.abarbach.gestionFactures.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping
    public ResponseEntity<List<FactureDto>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }
}