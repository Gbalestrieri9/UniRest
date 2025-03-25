package com.br.UniRest.adapters.repository.jpa;

import com.br.UniRest.adapters.repository.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity,Long> {


}
