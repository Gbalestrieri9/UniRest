package com.br.UniRest.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponseDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Long donoId;
    private String donoNome;
    private String donoEmail;
    private String donoRole;
}
