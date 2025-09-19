package com.NextTech.SOASprint.controller;

import java.awt.print.Pageable;
import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilResponseDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilUpdateDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilCreateDTO;
import com.NextTech.SOASprint.service.PerfilService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {
    private final PerfilService service;

    @Operation(summary = "Cria um novo perfil de investimento")
    @ApiResponses(responseCode = "201", description = "Perfil criado")
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody PerfilCreateDTO dto) {
        Long id = service.criar(dto);
        return ResponseEntity.created(URI.create("/perfis/" + id)).build();
    }

    @Operation(summary = "Lista perfis com paginação")
    @GetMapping
    public Page<PerfilResponseDTO> listar(@ParameterObject Pageable pageable) {
        return service.listar(pageable);
    }

    @Operation(summary = "Busca perfil por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza um perfil")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um perfil")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
