package com.NextTech.SOASprint.controller;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.dto.ProfileRegistrationRequest;
import com.NextTech.SOASprint.service.ProfileService; // Importa a Interface
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/profiles") // Caminho base para /profiles
public class ProfileController {

    // DIP/SOLID: Injeta a INTERFACE (ProfileService), não a classe de implementação (ProfileServiceImpl)
    private final ProfileService profileService; 

    // O Spring injeta a implementação concreta (ProfileServiceImpl) aqui
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * NOVO ENDPOINT DE REGISTRO (Cadastro de novo Perfil e Usuário)
     * Este endpoint deve ser PÚBLICO (configurado no SecurityConfigurations).
     * @param data DTO com dados do Perfil e credenciais.
     * @param uriBuilder Usado para construir o URI de resposta (melhores práticas REST).
     * @return 201 Created com o Perfil criado.
     */
    @PostMapping // POST para /profiles
    public ResponseEntity<Perfil> register(@RequestBody @Valid ProfileRegistrationRequest data,
                                         UriComponentsBuilder uriBuilder) {
        
        // Chama o método no Service que cria o Usuario (segurança) e o Perfil (negócio)
        Perfil newProfile = profileService.registerNewProfile(data);

        // Constrói o URI do novo recurso (melhores práticas REST)
        URI uri = uriBuilder.path("/profiles/{id}").buildAndExpand(newProfile.getId()).toUri();

        // Retorna 201 Created com o corpo da resposta e o URI no cabeçalho
        return ResponseEntity.created(uri).body(newProfile);
    }
    
    /**
     * Endpoint para buscar Perfil por ID.
     * Este endpoint agora está PROTEGIDO (configurado no SecurityConfigurations).
     * O usuário deve enviar um JWT válido para acessá-lo.
     * @param id O ID do Perfil.
     * @return 200 OK com o Perfil, ou 404 Not Found.
     */
    @GetMapping("/{id}") // GET para /profiles/{id}
    public ResponseEntity<Perfil> getProfileById(@PathVariable Long id) {
        return profileService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mantenha aqui todos os outros endpoints de CRUD (PUT, DELETE, etc.) que você já tinha.
}