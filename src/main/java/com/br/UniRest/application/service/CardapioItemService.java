package com.br.UniRest.application.service;

import com.br.UniRest.application.dto.CardapioItemDTO;
import com.br.UniRest.domain.model.CardapioItem;
import com.br.UniRest.domain.repository.CardapioItemRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioItemService {

    private final CardapioItemRepositoryPort cardapioItemRepositoryPort;

    public CardapioItemService(CardapioItemRepositoryPort cardapioItemRepositoryPort) {
        this.cardapioItemRepositoryPort = cardapioItemRepositoryPort;
    }

    public List<CardapioItemDTO> listarPorRestaurante(Long restauranteId) {
        List<CardapioItem> itens = cardapioItemRepositoryPort.buscarPorRestauranteId(restauranteId);

        return itens.stream()
                .map(this::toDTO)
                .toList();
    }

    private CardapioItemDTO toDTO(CardapioItem item) {
        CardapioItemDTO dto = new CardapioItemDTO();
        dto.setNome(item.getNome());
        dto.setDescricao(item.getDescricao());
        dto.setPreco(item.getPreco());
        dto.setDisponivelSomenteLocal(item.isDisponivelSomenteLocal());
        dto.setFotoPath(item.getFotoPath());
        return dto;
    }
}

