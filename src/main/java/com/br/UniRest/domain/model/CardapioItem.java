package com.br.UniRest.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CardapioItem {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponivelSomenteLocal;
    private String fotoPath;
    private Restaurante restaurante;

}
