package com.br.UniRest.adapters.web;

import com.br.UniRest.application.dto.LoginUserRequestDTO;
import com.br.UniRest.application.dto.LoginUserResponseDTO;
import com.br.UniRest.application.service.LoginUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Endpoints para login de usuários.")
public class UserController {

    private final LoginUserService loginUserService;

    public UserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @Operation(summary = "Login de usuário", description = "Realiza login com email e senha, retornando um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserRequestDTO request) {
        String token = loginUserService.execute(request);
        return ResponseEntity.ok(new LoginUserResponseDTO(token));
    }
}
