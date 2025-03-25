package com.br.UniRest.adapters.repository.jpa.impl;

import com.br.UniRest.adapters.repository.jpa.RestauranteJpaRepository;
import com.br.UniRest.adapters.repository.jpa.entity.RestauranteEntity;
import com.br.UniRest.adapters.repository.jpa.mapper.RestauranteEntityMapper;
import com.br.UniRest.domain.model.Restaurante;
import com.br.UniRest.domain.repository.RestauranteRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RestauranteRepository implements RestauranteRepositoryPort {

    private final RestauranteJpaRepository jpaRepository;

    public RestauranteRepository(RestauranteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = RestauranteEntityMapper.toEntity(restaurante);
        RestauranteEntity saved = jpaRepository.save(entity);
        return RestauranteEntityMapper.toDomain(saved);
    }

    @Override
    public List<Restaurante> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(RestauranteEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(RestauranteEntityMapper::toDomain);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }
}
