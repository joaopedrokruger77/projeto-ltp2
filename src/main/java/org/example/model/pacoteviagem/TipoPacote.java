package org.example.model.pacoteviagem;

public enum TipoPacote {
    AVENTURA("Aventura"),
    LUXO("Luxo"),
    CULTURAL("Cultural");

    private final String descricao;

    TipoPacote(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}