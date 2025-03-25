package com.br.UniRest.domain.model;

public enum TipoUsuarioEnum {
    CLIENTE("Cliente"),
    DONO("Dono de Restaurante"),
    ADMIN("Administrador");

    private final String nomeNoBanco;

    TipoUsuarioEnum(String nomeNoBanco) {
        this.nomeNoBanco = nomeNoBanco;
    }

    public String getNomeNoBanco() {
        return nomeNoBanco;
    }

    public static TipoUsuarioEnum fromNomeBanco(String nome) {
        if (nome == null) {
            return CLIENTE;
        }
        switch (nome.trim().toLowerCase()) {
            case "cliente":
                return CLIENTE;
            case "dono de restaurante":
                return DONO;
            case "administrador":
                return ADMIN;
            default:
                return CLIENTE;
        }
    }
}
