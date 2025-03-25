package com.br.UniRest.adapters.repository.jpa.mapper;

import com.br.UniRest.adapters.repository.jpa.entity.UsuarioEntity;
import com.br.UniRest.domain.model.Usuario;

public class UsuarioEntityMapper {

    public static UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setSenha(usuario.getSenha());
        entity.setTipoUsuario(
                TipoUsuarioEntityMapper.toEntity(usuario.getTipoUsuario())
        );

        return entity;
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setEmail(entity.getEmail());
        usuario.setSenha(entity.getSenha());
        usuario.setTipoUsuario(TipoUsuarioEntityMapper.toDomain(entity.getTipoUsuario()));

        return usuario;
    }
}
