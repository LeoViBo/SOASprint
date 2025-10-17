package com.NextTech.SOASprint.controller;

import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraCreateDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraResponseDTO;
import com.NextTech.SOASprint.dto.CarteiraDTOs.CarteiraUpdateDTO;
import com.NextTech.SOASprint.service.CarteiraService; // <-- Garante a injeção da Interface
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/carteiras")
@RequiredArgsConstructor
public class CarteiraController {

    // DIP/SOLID: A injeção é feita na INTERFACE (CarteiraService)
    private final CarteiraService service;

    @Operation(summary = "Cria uma carteira")
    @ApiResponse(responseCode = "201", description = "Carteira criada")
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody CarteiraCreateDTO dto) {
        Long id = service.criar(dto);
        return ResponseEntity.created(URI.create("/carteiras/" + id)).build();
    }

    @Operation(summary = "Lista carteiras com paginação")
    @GetMapping
    public Page<CarteiraResponseDTO> listar(@ParameterObject Pageable pageable) {
        return service.listar(pageable);
    }

    @Operation(summary = "Busca carteira por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CarteiraResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza uma carteira")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CarteiraUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove uma carteira")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}