package com.br.UniRest.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Long tipoUsuarioId;
}
