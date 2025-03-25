package com.br.UniRest.adapters.repository.jpa.entity;

import com.br.UniRest.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurante")
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private UsuarioEntity dono;

}
