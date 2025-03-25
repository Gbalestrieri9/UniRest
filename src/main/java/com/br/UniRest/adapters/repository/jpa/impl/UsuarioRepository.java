package com.br.UniRest.adapters.repository.jpa.impl;

import com.br.UniRest.adapters.repository.jpa.UsuarioJpaRepository;
import com.br.UniRest.adapters.repository.jpa.entity.UsuarioEntity;
import com.br.UniRest.adapters.repository.jpa.mapper.TipoUsuarioEntityMapper;
import com.br.UniRest.adapters.repository.jpa.mapper.UsuarioEntityMapper;
import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.domain.repository.UsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepository implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepository(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntityMapper.toEntity(usuario);
        UsuarioEntity saved = jpaRepository.save(entity);
        return UsuarioEntityMapper.toDomain(saved);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(UsuarioEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UsuarioEntityMapper::toDomain);
    }
}
