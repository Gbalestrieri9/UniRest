package com.br.UniRest.adapters.repository.jpa.impl;

import com.br.UniRest.adapters.repository.jpa.CardapioItemJpaRepository;
import com.br.UniRest.adapters.repository.jpa.entity.CardapioItemEntity;
import com.br.UniRest.adapters.repository.jpa.mapper.CardapioItemEntityMapper;
import com.br.UniRest.domain.model.CardapioItem;
import com.br.UniRest.domain.repository.CardapioItemRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardapioItemRepository implements CardapioItemRepositoryPort {

    private final CardapioItemJpaRepository jpaRepository;

    public CardapioItemRepository(CardapioItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CardapioItem salvar(CardapioItem item) {
        CardapioItemEntity entity = CardapioItemEntityMapper.toEntity(item);
        CardapioItemEntity saved = jpaRepository.save(entity);
        return CardapioItemEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<CardapioItem> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(CardapioItemEntityMapper::toDomain);
    }

    @Override
    public List<CardapioItem> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(CardapioItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }

    // Novo método
    @Override
    public List<CardapioItem> buscarPorRestauranteId(Long restauranteId) {
        // Usa um método custom do JPA
        return jpaRepository.findByRestaurante_Id(restauranteId).stream()
                .map(CardapioItemEntityMapper::toDomain)
                .toList();
    }
}
