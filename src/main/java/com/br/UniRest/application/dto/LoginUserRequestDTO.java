package com.br.UniRest.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDTO {
    private String email;
    private String password;
}
