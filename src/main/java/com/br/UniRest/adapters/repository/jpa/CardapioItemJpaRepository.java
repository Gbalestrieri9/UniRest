package com.br.UniRest.adapters.repository.jpa;

import com.br.UniRest.adapters.repository.jpa.entity.CardapioItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardapioItemJpaRepository extends JpaRepository<CardapioItemEntity, Long> {
    List<CardapioItemEntity> findByRestaurante_Id(Long restauranteId);
}

