package com.br.UniRest;

import com.br.UniRest.adapters.web.RestauranteController;
import com.br.UniRest.application.dto.RestauranteDTO;
import com.br.UniRest.application.dto.RestauranteResponseDTO;
import com.br.UniRest.application.service.CardapioItemService;
import com.br.UniRest.application.service.RestauranteService;
import com.br.UniRest.domain.model.Restaurante;
import com.br.UniRest.domain.model.Usuario;
import com.br.UniRest.infra.security.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestauranteController.class)
// Desabilita filtros de segurança (JWT) para não receber 403 automaticamente
@AutoConfigureMockMvc(addFilters = false)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock as dependências que o controller injeta via construtor
    @MockBean
    private RestauranteService restauranteService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CardapioItemService cardapioItemService;

    @Test
    void deveCriarRestauranteComSucesso_EnviandoAuthorizationHeaderFake() throws Exception {
        // 1) Configura mocks para não dar NullPointer no substring(7) e simular token válido
        given(jwtUtil.validateToken("test")).willReturn(true);
        given(jwtUtil.getUserId("test")).willReturn(5L);

        // 2) Simula retorno do service
        Restaurante restauranteSalvo = new Restaurante();
        restauranteSalvo.setId(100L);
        restauranteSalvo.setNome("Novo Restaurante");
        Usuario dono = new Usuario();
        dono.setId(5L);
        restauranteSalvo.setDono(dono);

        given(restauranteService.criar(any(RestauranteDTO.class), eq(5L)))
                .willReturn(restauranteSalvo);

        // 3) Monta JSON do body
        String jsonBody = """
                {
                  "nome": "Novo Restaurante",
                  "endereco": "Rua X",
                  "tipoCozinha": "Italiana",
                  "horarioFuncionamento": "08:00-22:00"
                }
                """;

        // 4) Executa a requisição com o header Authorization
        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        // Passamos "Bearer test" para evitar NullPointerException em substring(7)
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.nome").value("Novo Restaurante"));
    }
}
