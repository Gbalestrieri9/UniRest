package com.br.UniRest.adapters.repository.jpa.mapper;

import com.br.UniRest.adapters.repository.jpa.entity.CardapioItemEntity;
import com.br.UniRest.domain.model.CardapioItem;

public class CardapioItemEntityMapper {

    public static CardapioItemEntity toEntity(CardapioItem item) {
        if (item == null) {
            return null;
        }

        CardapioItemEntity entity = new CardapioItemEntity();
        entity.setId(item.getId());
        entity.setNome(item.getNome());
        entity.setDescricao(item.getDescricao());
        entity.setPreco(item.getPreco());
        entity.setDisponivelSomenteLocal(item.isDisponivelSomenteLocal());
        entity.setFotoPath(item.getFotoPath());
        entity.setRestaurante(RestauranteEntityMapper.toEntity(item.getRestaurante()));

        return entity;
    }

    public static CardapioItem toDomain(CardapioItemEntity entity) {
        if (entity == null) {
            return null;
        }

        CardapioItem item = new CardapioItem();
        item.setId(entity.getId());
        item.setNome(entity.getNome());
        item.setDescricao(entity.getDescricao());
        item.setPreco(entity.getPreco());
        item.setDisponivelSomenteLocal(entity.isDisponivelSomenteLocal());
        item.setFotoPath(entity.getFotoPath());
        item.setRestaurante(RestauranteEntityMapper.toDomain(entity.getRestaurante()));

        return item;
    }
}

