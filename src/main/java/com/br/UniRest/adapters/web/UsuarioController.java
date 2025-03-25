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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuário", description = "Endpoints para criação de usuários do tipo CLIENTE.")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JWTUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JWTUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Criar usuário (CLIENTE)", description = "Cria um novo usuário do tipo CLIENTE.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioCriado = usuarioService.createUserCliente(usuarioDTO);

        UsuarioDTO resposta = new UsuarioDTO();
        resposta.setId(usuarioCriado.getId());
        resposta.setNome(usuarioCriado.getNome());
        resposta.setEmail(usuarioCriado.getEmail());

        return ResponseEntity.ok(resposta);
    }
}
