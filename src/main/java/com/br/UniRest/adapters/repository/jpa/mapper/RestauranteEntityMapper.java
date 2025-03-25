package com.br.UniRest.adapters.repository.jpa.mapper;

import com.br.UniRest.adapters.repository.jpa.entity.RestauranteEntity;
import com.br.UniRest.domain.model.Restaurante;

public class RestauranteEntityMapper {

    public static RestauranteEntity toEntity(Restaurante restaurante) {
        RestauranteEntity entity = new RestauranteEntity();
        entity.setId(restaurante.getId());
        entity.setNome(restaurante.getNome());
        entity.setEndereco(restaurante.getEndereco());
        entity.setTipoCozinha(restaurante.getTipoCozinha());
        entity.setHorarioFuncionamento(restaurante.getHorarioFuncionamento());
        entity.setDono(UsuarioEntityMapper.toEntity(restaurante.getDono()));
        return entity;
    }

    public static Restaurante toDomain(RestauranteEntity entity) {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(entity.getId());
        restaurante.setNome(entity.getNome());
        restaurante.setEndereco(entity.getEndereco());
        restaurante.setTipoCozinha(entity.getTipoCozinha());
        restaurante.setHorarioFuncionamento(entity.getHorarioFuncionamento());
        restaurante.setDono(UsuarioEntityMapper.toDomain(entity.getDono()));
        return restaurante;
    }
}
