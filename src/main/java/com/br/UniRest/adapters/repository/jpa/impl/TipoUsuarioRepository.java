package com.br.UniRest.adapters.repository.jpa.impl;

import com.br.UniRest.adapters.repository.jpa.TipoUsuarioJpaRepository;
import com.br.UniRest.adapters.repository.jpa.entity.TipoUsuarioEntity;
import com.br.UniRest.adapters.repository.jpa.mapper.TipoUsuarioEntityMapper;
import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.repository.TipoUsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TipoUsuarioRepository implements TipoUsuarioRepositoryPort {

    private final TipoUsuarioJpaRepository jpaRepository;

    public TipoUsuarioRepository(TipoUsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntity entity = TipoUsuarioEntityMapper.toEntity(tipoUsuario);
        TipoUsuarioEntity saved = jpaRepository.save(entity);
        return TipoUsuarioEntityMapper.toDomain(saved);
    }

    @Override
    public List<TipoUsuario> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(TipoUsuarioEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(TipoUsuarioEntityMapper::toDomain);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<TipoUsuario> buscarPorNome(String nome) {
        return jpaRepository.findByNome(nome)
                .map(TipoUsuarioEntityMapper::toDomain);
    }
}
