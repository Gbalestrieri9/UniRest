package com.br.UniRest.adapters.web;

import com.br.UniRest.application.dto.UsuarioDTO;
import com.br.UniRest.application.service.UsuarioService;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.infra.security.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donos")
@Tag(name = "Dono", description = "Endpoints relacionados à criação de Donos (somente ADMIN).")
public class DonoController {

    private final UsuarioService usuarioService;
    private final JWTUtil jwtUtil;

    public DonoController(UsuarioService usuarioService, JWTUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Criar Dono",
            description = "Cria um novo usuário do tipo Dono. Somente um usuário com role=ADMIN pode executar."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dono criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Somente ADMIN pode criar Dono"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarDono(@RequestBody UsuarioDTO usuarioDTO,
                                                HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String roleDoUsuarioLogado = jwtUtil.getRole(token);

        Usuario usuarioCriado = usuarioService.createUserDono(usuarioDTO, roleDoUsuarioLogado);

        UsuarioDTO resposta = new UsuarioDTO();
        resposta.setId(usuarioCriado.getId());
        resposta.setNome(usuarioCriado.getNome());
        resposta.setEmail(usuarioCriado.getEmail());
        return ResponseEntity.ok(resposta);
    }
}
