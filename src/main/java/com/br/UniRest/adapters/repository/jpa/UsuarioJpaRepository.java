package com.br.UniRest.adapters.repository.jpa;

import com.br.UniRest.adapters.repository.jpa.entity.TipoUsuarioEntity;
import com.br.UniRest.adapters.repository.jpa.entity.UsuarioEntity;
import com.br.UniRest.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
  Optional<UsuarioEntity> findByEmail(String email);
}
