package com.br.UniRest;

import com.br.UniRest.adapters.repository.jpa.entity.UsuarioEntity;
import com.br.UniRest.application.dto.CardapioItemDTO;
import com.br.UniRest.application.dto.RestauranteDTO;
import com.br.UniRest.application.service.RestauranteService;
import com.br.UniRest.domain.exception.AcessoNegadoException;
import com.br.UniRest.domain.exception.ResourceNotFoundException;
import com.br.UniRest.domain.model.CardapioItem;
import com.br.UniRest.domain.model.Restaurante;
import com.br.UniRest.domain.model.TipoUsuario;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.domain.repository.CardapioItemRepositoryPort;
import com.br.UniRest.domain.repository.RestauranteRepositoryPort;
import com.br.UniRest.domain.repository.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class RestauranteServiceTest {

    private RestauranteRepositoryPort restauranteRepositoryPort;
    private CardapioItemRepositoryPort cardapioItemRepositoryPort;
    private UsuarioRepositoryPort usuarioRepositoryPort;
    private RestauranteService restauranteService;

    // Mock extra que você tem no construtor
    private com.br.UniRest.adapters.repository.jpa.UsuarioJpaRepository usuarioJpaRepository;

    @BeforeEach
    void setUp() {
        // Cria mocks
        restauranteRepositoryPort = Mockito.mock(RestauranteRepositoryPort.class);
        cardapioItemRepositoryPort = Mockito.mock(CardapioItemRepositoryPort.class);
        usuarioRepositoryPort = Mockito.mock(UsuarioRepositoryPort.class);
        usuarioJpaRepository = Mockito.mock(com.br.UniRest.adapters.repository.jpa.UsuarioJpaRepository.class);

        // Instancia o service com os mocks
        restauranteService = new RestauranteService(
                restauranteRepositoryPort,
                usuarioJpaRepository,
                cardapioItemRepositoryPort,
                usuarioRepositoryPort
        );
    }

    @Test
    void deveListarRestaurantes() {
        // Dado que o repo retorna 2 restaurantes
        Restaurante r1 = new Restaurante();
        r1.setId(1L);
        r1.setNome("Rest A");
        Restaurante r2 = new Restaurante();
        r2.setId(2L);
        r2.setNome("Rest B");

        given(restauranteRepositoryPort.buscarTodos()).willReturn(List.of(r1, r2));

        // Quando
        List<Restaurante> resultado = restauranteService.listar();

        // Então
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Rest A", resultado.get(0).getNome());
        then(restauranteRepositoryPort).should(times(1)).buscarTodos();
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        // Dado
        Restaurante r = new Restaurante();
        r.setId(10L);
        r.setNome("Restaurante X");

        given(restauranteRepositoryPort.buscarPorId(10L)).willReturn(Optional.of(r));

        // Quando
        Restaurante resultado = restauranteService.buscarPorId(10L);

        // Então
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Restaurante X", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoSeRestauranteNaoEncontrado() {
        // Dado
        given(restauranteRepositoryPort.buscarPorId(99L)).willReturn(Optional.empty());

        // Quando & Então
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteService.buscarPorId(99L));
    }

    @Test
    void deveCriarRestauranteComSucesso() {
        // Dado
        RestauranteDTO dto = new RestauranteDTO();
        dto.setNome("Novo Restaurante");
        dto.setEndereco("Endereço Teste");
        dto.setTipoCozinha("Italiana");
        dto.setHorarioFuncionamento("08:00-22:00");

        // Dono mockado
        Usuario dono = new Usuario();
        dono.setId(5L);
        dono.setNome("João Dono");
        TipoUsuario tipo = new TipoUsuario();
        // ... set enumTipo, etc. se precisar
        dono.setTipoUsuario(tipo);

        given(usuarioRepositoryPort.buscarPorId(5L)).willReturn(Optional.of(dono));

        // Quando salvar, vamos simular o repositório retornando um Restaurante com ID gerado
        Restaurante salvo = new Restaurante();
        salvo.setId(100L);
        salvo.setNome("Novo Restaurante");
        salvo.setDono(dono);

        given(restauranteRepositoryPort.salvar(any(Restaurante.class))).willReturn(salvo);

        // Quando
        Restaurante resultado = restauranteService.criar(dto, 5L);

        // Então
        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Novo Restaurante", resultado.getNome());
        assertEquals(5L, resultado.getDono().getId());
    }

    @Test
    void deveLancarExcecaoAoCriarRestauranteComDonoInexistente() {
        // Dado
        RestauranteDTO dto = new RestauranteDTO();
        dto.setNome("Rest sem dono");
        given(usuarioRepositoryPort.buscarPorId(999L)).willReturn(Optional.empty());

        // Quando & Então
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteService.criar(dto, 999L));
    }

    @Test
    void deveAtualizarRestauranteComNovoDono() {
        // Dado
        // restauranteExistente
        Restaurante restauranteExistente = new Restaurante();
        restauranteExistente.setId(1L);
        restauranteExistente.setNome("Rest Antigo");
        Usuario donoAntigo = new Usuario();
        donoAntigo.setId(10L);
        restauranteExistente.setDono(donoAntigo);

        given(restauranteRepositoryPort.buscarPorId(1L)).willReturn(Optional.of(restauranteExistente));

        // Dono novo
        UsuarioEntity novoDonoEntity = new UsuarioEntity();
        novoDonoEntity.setId(20L);
        novoDonoEntity.setNome("Novo Dono");
        // etc.

        given(usuarioJpaRepository.findById(20L)).willReturn(Optional.of(novoDonoEntity));

        // simulamos que ao salvar, retorna o mesmo com dados atualizados
        Restaurante salvo = new Restaurante();
        salvo.setId(1L);
        salvo.setNome("Rest Atualizado");
        Usuario donoNovoDomain = new Usuario();
        donoNovoDomain.setId(20L);
        salvo.setDono(donoNovoDomain);

        given(restauranteRepositoryPort.salvar(any(Restaurante.class))).willReturn(salvo);

        // Monta dto
        RestauranteDTO dto = new RestauranteDTO();
        dto.setNome("Rest Atualizado");
        dto.setDonoId(20L);

        // Quando
        Restaurante resultado = restauranteService.atualizar(1L, dto);

        // Então
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rest Atualizado", resultado.getNome());
        assertEquals(20L, resultado.getDono().getId());
    }

    @Test
    void deveLancarExcecaoAoAtualizarRestauranteInexistente() {
        given(restauranteRepositoryPort.buscarPorId(999L)).willReturn(Optional.empty());
        RestauranteDTO dto = new RestauranteDTO();
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteService.atualizar(999L, dto));
    }

    @Test
    void deveDeletarComSucesso() {
        // Dado
        Restaurante r = new Restaurante();
        r.setId(2L);
        given(restauranteRepositoryPort.buscarPorId(2L)).willReturn(Optional.of(r));

        // Quando
        restauranteService.deletar(2L);

        // Então
        then(restauranteRepositoryPort).should(times(1)).deletar(2L);
    }

    @Test
    void deveCriarItemCardapioComSucesso() {
        // Dado
        Restaurante rest = new Restaurante();
        rest.setId(10L);
        Usuario dono = new Usuario();
        dono.setId(5L);
        rest.setDono(dono);

        given(restauranteRepositoryPort.buscarPorId(10L)).willReturn(Optional.of(rest));

        CardapioItemDTO dto = new CardapioItemDTO();
        dto.setNome("Pizza");
        dto.setDescricao("Pizza de Mussarela");
        dto.setPreco(BigDecimal.valueOf(35.0));
        dto.setDisponivelSomenteLocal(true);
        dto.setFotoPath("/img/pizza.jpg");

        // Simula repositório de cardápio
        CardapioItem itemSalvo = new CardapioItem();
        itemSalvo.setId(100L);
        itemSalvo.setNome("Pizza");
        given(cardapioItemRepositoryPort.salvar(any(CardapioItem.class))).willReturn(itemSalvo);

        // Quando
        CardapioItem resultado = restauranteService.criarItemCardapio(10L, dto, 5L);

        // Então
        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Pizza", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoSeNaoForDono() {
        // Dado
        Restaurante rest = new Restaurante();
        rest.setId(10L);
        Usuario dono = new Usuario();
        dono.setId(5L);
        rest.setDono(dono);

        given(restauranteRepositoryPort.buscarPorId(10L)).willReturn(Optional.of(rest));

        CardapioItemDTO dto = new CardapioItemDTO();
        dto.setNome("Pizza");

        // Quando & Então
        assertThrows(AcessoNegadoException.class,
                () -> restauranteService.criarItemCardapio(10L, dto, 999L));
    }

    @Test
    void deveLancarExcecaoSeRestauranteNaoExisteAoCriarItem() {
        given(restauranteRepositoryPort.buscarPorId(999L)).willReturn(Optional.empty());
        CardapioItemDTO dto = new CardapioItemDTO();
        assertThrows(ResourceNotFoundException.class,
                () -> restauranteService.criarItemCardapio(999L, dto, 5L));
    }
}
