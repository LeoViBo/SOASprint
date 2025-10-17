package com.NextTech.SOASprint.service;

import com.NextTech.SOASprint.domain.model.Perfil;
import com.NextTech.SOASprint.domain.model.Usuario;
import com.NextTech.SOASprint.domain.model.vo.EmailVO;
import com.NextTech.SOASprint.dto.ProfileRegistrationRequest;
import com.NextTech.SOASprint.repository.PerfilRepository;
import com.NextTech.SOASprint.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository; 
    @Autowired
    private PasswordEncoder passwordEncoder;     

    public ProfileServiceImpl(PerfilRepository perfilRepository,
                              UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Perfil registerNewProfile(ProfileRegistrationRequest data) {
        String hashedPassword = passwordEncoder.encode(data.getPassword());

        Usuario novoUsuario = new Usuario(data.getEmail(), hashedPassword);
        usuarioRepository.save(novoUsuario);

        Perfil novoPerfil = Perfil.builder()
                .email(new EmailVO(data.getEmail()))
                .nome(data.getNome())
                .objetivoFinanceiro(data.getObjetivoFinanceiro())
                .toleranciaRisco(data.getToleranciaRisco())
                .valorParaInvesimento(data.getValorParaInvesimento())
                .horizonteDeTempo(data.getHorizonteDeTempo())
                .build();

        return perfilRepository.save(novoPerfil);
    }
    @Override
    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }
}