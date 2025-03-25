package com.br.UniRest.adapters.web;

import com.br.UniRest.application.dto.CardapioItemDTO;
import com.br.UniRest.application.dto.RestauranteResponseDTO;
import com.br.UniRest.application.service.CardapioItemService;
import com.br.UniRest.application.service.RestauranteService;
import com.br.UniRest.application.dto.RestauranteDTO;
import com.br.UniRest.domain.model.Restaurante;
import com.br.UniRest.infra.security.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurante", description = "Endpoints para gerenciamento de restaurantes e seus itens de cardápio.")
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final JWTUtil jwtUtil;
    private final CardapioItemService cardapioItemService;

    public RestauranteController(RestauranteService restauranteService,
                                 JWTUtil jwtUtil,
                                 CardapioItemService cardapioItemService) {
        this.restauranteService = restauranteService;
        this.jwtUtil = jwtUtil;
        this.cardapioItemService = cardapioItemService;
    }

    @Operation(summary = "Listar restaurantes", description = "Retorna a lista de todos os restaurantes cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os dados de um restaurante específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id);
    }

    @Operation(summary = "Criar restaurante", description = "Cria um novo restaurante. Somente DONO pode criar.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente DONO)"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criar(@RequestBody RestauranteDTO dto,
                                                        HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long donoId = jwtUtil.getUserId(token);

        Restaurante restauranteCriado = restauranteService.criar(dto, donoId);

        RestauranteResponseDTO response = new RestauranteResponseDTO();
        response.setId(restauranteCriado.getId());
        response.setNome(restauranteCriado.getNome());
        response.setEndereco(restauranteCriado.getEndereco());
        response.setTipoCozinha(restauranteCriado.getTipoCozinha());
        response.setHorarioFuncionamento(restauranteCriado.getHorarioFuncionamento());

        if (restauranteCriado.getDono() != null) {
            response.setDonoId(restauranteCriado.getDono().getId());
            response.setDonoNome(restauranteCriado.getDono().getNome());
            response.setDonoEmail(restauranteCriado.getDono().getEmail());

            if (restauranteCriado.getDono().getTipoUsuario() != null) {
                response.setDonoRole(restauranteCriado.getDono().getTipoUsuario().getEnumTipo().name());
            }
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar restaurante", description = "Atualiza os dados de um restaurante existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody RestauranteDTO restauranteDTO) {
        return restauranteService.atualizar(id, restauranteDTO);
    }

    @Operation(summary = "Deletar restaurante", description = "Remove um restaurante pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
    }

    @Operation(summary = "Criar item de cardápio", description = "Cria um novo item de cardápio para o restaurante. Somente DONO do restaurante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente DONO do restaurante)"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("/{restauranteId}/cardapio-itens")
    public ResponseEntity<?> criarItemCardapio(
            @PathVariable Long restauranteId,
            @RequestBody CardapioItemDTO itemDTO,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token ausente ou inválido");
        }
        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        String role = jwtUtil.getRole(token);
        if (!"DONO".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Somente DONO pode criar itens de cardápio");
        }

        Long donoId = jwtUtil.getUserId(token);
        var itemCriado = restauranteService.criarItemCardapio(restauranteId, itemDTO, donoId);

        return ResponseEntity.ok(itemCriado);
    }

    @Operation(summary = "Listar itens de cardápio", description = "Retorna a lista de itens de cardápio de um restaurante específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/{restauranteId}/cardapio-itens")
    public List<CardapioItemDTO> listarCardapio(@PathVariable Long restauranteId) {
        return cardapioItemService.listarPorRestaurante(restauranteId);
    }
}
