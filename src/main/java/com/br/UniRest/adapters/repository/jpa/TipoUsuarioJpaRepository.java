package com.br.UniRest.adapters.repository.jpa;

import com.br.UniRest.adapters.repository.jpa.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioJpaRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    Optional<TipoUsuarioEntity> findByNome(String nome);
}