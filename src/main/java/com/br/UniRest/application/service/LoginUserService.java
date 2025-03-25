package com.br.UniRest.application.service;

import com.br.UniRest.application.dto.LoginUserRequestDTO;
import com.br.UniRest.domain.model.TipoUsuarioEnum;
import com.br.UniRest.domain.repository.UsuarioRepositoryPort;
import com.br.UniRest.infra.security.jwt.JWTUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final JWTUtil jwtUtil;

    public LoginUserService(UsuarioRepositoryPort usuarioRepositoryPort, JWTUtil jwtUtil) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.jwtUtil = jwtUtil;
    }

    public String execute(LoginUserRequestDTO request) {
        var usuarioOpt = usuarioRepositoryPort.buscarPorEmail(request.getEmail());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado: " + request.getEmail());
        }

        var usuario = usuarioOpt.get();

        var tipoUsuario = usuario.getTipoUsuario();
        TipoUsuarioEnum enumTipo = (tipoUsuario != null)
                ? tipoUsuario.getEnumTipo()
                : TipoUsuarioEnum.CLIENTE; // fallback

        String rolePadronizada = enumTipo.name();
        String token = jwtUtil.generateToken(usuario.getId(), usuario.getNome(), rolePadronizada);

        return token;
    }
}


