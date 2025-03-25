package com.br.UniRest.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardapioItemDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivelSomenteLocal;
    private String fotoPath;
}
