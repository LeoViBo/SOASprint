package com.NextTech.SOASprint.controller;

import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilCreateDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilResponseDTO;
import com.NextTech.SOASprint.dto.PerfilDTOs.PerfilUpdateDTO;
import com.NextTech.SOASprint.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private PerfilService service;

    @Operation(summary = "Cria um novo perfil de investimento")
    @ApiResponse(responseCode = "201", description = "Perfil criado")
    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody PerfilCreateDTO dto) {
        Long id = service.criar(dto); 
        
        return ResponseEntity.created(URI.create("/perfis/" + id)).build();
    }

    @Operation(summary = "Lista perfis com paginação")
    @GetMapping
    public ResponseEntity<Page<PerfilResponseDTO>> listar(@ParameterObject Pageable pageable) {
        Page<PerfilResponseDTO> page = service.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Busca perfil por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> getById(@PathVariable Long id) {
        try {
            PerfilResponseDTO response = service.getById(id);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza um perfil existente")
    @ApiResponse(responseCode = "204", description = "Perfil atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilUpdateDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um perfil")
    @ApiResponse(responseCode = "204", description = "Perfil removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
