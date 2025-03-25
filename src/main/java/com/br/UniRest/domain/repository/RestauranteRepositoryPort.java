package com.br.UniRest.domain.repository;

import com.br.UniRest.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepositoryPort {
    Restaurante salvar(Restaurante restaurante);
    List<Restaurante> buscarTodos();
    Optional<Restaurante> buscarPorId(Long id);
    void deletar(Long id);
}
