package com.br.UniRest.domain.repository;

import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);
    List<Usuario> buscarTodos();
    Optional<Usuario> buscarPorId(Long id);
    void deletar(Long id);
    Optional<Usuario> buscarPorEmail(String email);
}
