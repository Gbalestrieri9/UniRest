package com.br.UniRest;

import com.br.UniRest.adapters.repository.jpa.RestauranteJpaRepository;
import com.br.UniRest.adapters.repository.jpa.entity.RestauranteEntity;
import com.br.UniRest.adapters.repository.jpa.impl.RestauranteRepository;
import com.br.UniRest.domain.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteRepositoryTest {

    @Autowired
    private RestauranteJpaRepository jpaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;
    // Este é o adapter que implementa RestauranteRepositoryPort

    @Test
    void deveSalvarRestauranteEBuscarPorId() {
        // 1) Criar e salvar entity via adapter
        Restaurante r = new Restaurante();
        r.setNome("Rest Test");
        r.setEndereco("End Test");
        r.setTipoCozinha("Italiana");
        r.setHorarioFuncionamento("09:00-18:00");

        Restaurante salvo = restauranteRepository.salvar(r);
        assertNotNull(salvo.getId());

        // 2) Buscar por id
        var optRest = restauranteRepository.buscarPorId(salvo.getId());
        assertTrue(optRest.isPresent());
        assertEquals("Rest Test", optRest.get().getNome());
    }

    @Test
    void deveListarTodosRestaurantes() {
        // 1) Salvar 2
        Restaurante r1 = new Restaurante();
        r1.setNome("Rest1");
        restauranteRepository.salvar(r1);

        Restaurante r2 = new Restaurante();
        r2.setNome("Rest2");
        restauranteRepository.salvar(r2);

        // 2) Buscar todos
        List<Restaurante> lista = restauranteRepository.buscarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void deveDeletarRestaurante() {
        Restaurante r = new Restaurante();
        r.setNome("Rest to delete");
        Restaurante salvo = restauranteRepository.salvar(r);

        // deletar
        restauranteRepository.deletar(salvo.getId());

        // deve estar vazio
        var opt = restauranteRepository.buscarPorId(salvo.getId());
        assertFalse(opt.isPresent());
    }
}
