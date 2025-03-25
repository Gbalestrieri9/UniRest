package com.br.UniRest.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Long donoId;
}
