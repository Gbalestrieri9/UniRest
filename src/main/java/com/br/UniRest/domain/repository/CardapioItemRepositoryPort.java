package com.br.UniRest.domain.repository;

import com.br.UniRest.domain.model.CardapioItem;

import java.util.List;
import java.util.Optional;

public interface CardapioItemRepositoryPort {
    CardapioItem salvar(CardapioItem item);
    Optional<CardapioItem> buscarPorId(Long id);
    List<CardapioItem> buscarTodos();
    void deletar(Long id);
    List<CardapioItem> buscarPorRestauranteId(Long restauranteId);
}
