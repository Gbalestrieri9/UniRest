package com.br.UniRest.domain.repository;

import com.br.UniRest.domain.model.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepositoryPort {

    TipoUsuario salvar(TipoUsuario tipoUsuario);
    List<TipoUsuario> buscarTodos();
    Optional<TipoUsuario> buscarPorId(Long id);
    void deletar(Long id);
    Optional<TipoUsuario> buscarPorNome(String nome);
}
