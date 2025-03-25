package com.br.UniRest.application.service;

import com.br.UniRest.application.dto.UsuarioDTO;
import com.br.UniRest.domain.exception.AcessoNegadoException;
import com.br.UniRest.domain.exception.ResourceNotFoundException;
import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.domain.repository.TipoUsuarioRepositoryPort;
import com.br.UniRest.domain.repository.UsuarioRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final TipoUsuarioRepositoryPort tipoUsuarioRepositoryPort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                          TipoUsuarioRepositoryPort tipoUsuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.tipoUsuarioRepositoryPort = tipoUsuarioRepositoryPort;
    }

    public Usuario createUserCliente(UsuarioDTO usuarioDTO) {
        log.info("Iniciando criação de usuário do tipo CLIENTE com DTO: {}", usuarioDTO);

        TipoUsuario tipoCliente = tipoUsuarioRepositoryPort.buscarPorNome("Cliente")
                .orElseThrow(() -> {
                    log.error("Tipo 'Cliente' não encontrado no banco.");
                    return new ResourceNotFoundException("Tipo 'Cliente' não encontrado");
                });

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTipoUsuario(tipoCliente);

        log.debug("Objeto de domínio montado: {}", usuario);

        Usuario usuarioSalvo = usuarioRepositoryPort.salvar(usuario);
        log.info("Usuário CLIENTE salvo com ID: {}", usuarioSalvo.getId());

        return usuarioSalvo;
    }

    public Usuario createUserDono(UsuarioDTO usuarioDTO, String roleDoUsuarioLogado) {
        log.info("Iniciando criação de usuário do tipo DONO. Role do usuário logado: {}", roleDoUsuarioLogado);

        if (!"ADMIN".equalsIgnoreCase(roleDoUsuarioLogado)) {
            log.warn("Acesso negado. roleDoUsuarioLogado não é ADMIN.");
            throw new AcessoNegadoException("Somente ADMIN pode criar DONO");
        }

        TipoUsuario tipoDono = tipoUsuarioRepositoryPort.buscarPorNome("Dono de Restaurante")
                .orElseThrow(() -> {
                    log.error("Tipo 'Dono de Restaurante' não encontrado no banco.");
                    return new ResourceNotFoundException("Tipo 'Dono de Restaurante' não encontrado");
                });

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTipoUsuario(tipoDono);

        log.debug("Objeto de domínio (DONO) montado: {}", usuario);

        Usuario usuarioSalvo = usuarioRepositoryPort.salvar(usuario);
        log.info("Usuário DO tipo DONO salvo com ID: {}", usuarioSalvo.getId());

        return usuarioSalvo;
    }
}
