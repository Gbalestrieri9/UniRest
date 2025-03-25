package com.br.UniRest.adapters.repository.jpa.mapper;

import com.br.UniRest.adapters.repository.jpa.entity.TipoUsuarioEntity;
import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.model.TipoUsuarioEnum;

public class TipoUsuarioEntityMapper {

    public static TipoUsuario toDomain(TipoUsuarioEntity entity) {
        if (entity == null) return null;
        TipoUsuario domain = new TipoUsuario();
        domain.setId(entity.getId());
        domain.setEnumTipo(TipoUsuarioEnum.fromNomeBanco(entity.getNome()));
        return domain;
    }

    public static TipoUsuarioEntity toEntity(TipoUsuario domain) {
        if (domain == null) return null;
        TipoUsuarioEntity entity = new TipoUsuarioEntity();
        entity.setId(domain.getId());
        if (domain.getEnumTipo() != null) {
            entity.setNome(domain.getEnumTipo().getNomeNoBanco());
        } else {
            entity.setNome(null);
        }
        return entity;
    }
}
