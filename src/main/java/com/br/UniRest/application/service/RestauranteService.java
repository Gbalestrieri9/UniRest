package com.br.UniRest.application.service;

import com.br.UniRest.application.dto.CardapioItemDTO;
import com.br.UniRest.domain.exception.AcessoNegadoException;
import com.br.UniRest.domain.model.CardapioItem;
import com.br.UniRest.domain.model.Restaurante;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.domain.exception.ResourceNotFoundException;
import com.br.UniRest.domain.repository.CardapioItemRepositoryPort;
import com.br.UniRest.domain.repository.RestauranteRepositoryPort;
import com.br.UniRest.application.dto.RestauranteDTO;
import com.br.UniRest.adapters.repository.jpa.UsuarioJpaRepository;
import com.br.UniRest.adapters.repository.jpa.mapper.UsuarioEntityMapper;
import com.br.UniRest.domain.repository.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepositoryPort restauranteRepositoryPort;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final CardapioItemRepositoryPort cardapioItemRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public RestauranteService(RestauranteRepositoryPort restauranteRepositoryPort,
                              UsuarioJpaRepository usuarioJpaRepository,
                              CardapioItemRepositoryPort cardapioItemRepositoryPort,
                              UsuarioRepositoryPort usuarioRepositoryPort) {
        this.restauranteRepositoryPort = restauranteRepositoryPort;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.cardapioItemRepositoryPort = cardapioItemRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    public List<Restaurante> listar() {
        return restauranteRepositoryPort.buscarTodos();
    }

    public Restaurante buscarPorId(Long id) {
        return restauranteRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));
    }

    public Restaurante criar(RestauranteDTO dto, Long donoId) {
        Usuario donoDomain = usuarioRepositoryPort.buscarPorId(donoId)
                .orElseThrow(() -> new ResourceNotFoundException("Dono não encontrado"));
        Restaurante r = new Restaurante();
        r.setNome(dto.getNome());
        r.setEndereco(dto.getEndereco());
        r.setTipoCozinha(dto.getTipoCozinha());
        r.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        r.setDono(donoDomain);

        return restauranteRepositoryPort.salvar(r);
    }

    public Restaurante atualizar(Long id, RestauranteDTO restauranteDTO) {
        Restaurante restauranteExistente = buscarPorId(id);

        if (restauranteDTO.getDonoId() != null) {
            var novoDonoEntity = usuarioJpaRepository.findById(restauranteDTO.getDonoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Usuário não encontrado com ID: " + restauranteDTO.getDonoId()
                    ));
            Usuario novoDonoDomain = UsuarioEntityMapper.toDomain(novoDonoEntity);
            restauranteExistente.setDono(novoDonoDomain);
        }

        restauranteExistente.setNome(restauranteDTO.getNome());
        restauranteExistente.setEndereco(restauranteDTO.getEndereco());
        restauranteExistente.setTipoCozinha(restauranteDTO.getTipoCozinha());
        restauranteExistente.setHorarioFuncionamento(restauranteDTO.getHorarioFuncionamento());

        return restauranteRepositoryPort.salvar(restauranteExistente);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        restauranteRepositoryPort.deletar(id);
    }

    public CardapioItem criarItemCardapio(Long restauranteId, CardapioItemDTO dto, Long donoId) {
        Restaurante restaurante = restauranteRepositoryPort.buscarPorId(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado com ID: " + restauranteId
                ));

        if (!restaurante.getDono().getId().equals(donoId)) {
            throw new AcessoNegadoException("Você não é dono deste restaurante");
        }

        CardapioItem item = new CardapioItem();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setDisponivelSomenteLocal(dto.getDisponivelSomenteLocal());
        item.setFotoPath(dto.getFotoPath());
        item.setRestaurante(restaurante);

        CardapioItem itemSalvo = cardapioItemRepositoryPort.salvar(item);
        return itemSalvo;
    }
}
