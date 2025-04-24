package org.example.model;

public class PacoteAventura extends PacoteViagem {
    private String equipamentoIncluso;

    public PacoteAventura(String nome, String destino, int duracao, double preco, String equipamentoIncluso) {
        super(nome, destino, duracao, preco, "aventura");
        this.equipamentoIncluso = equipamentoIncluso;
    }

    @Override
    public String getDetalhes() {
        return "Equipamento incluso: " + equipamentoIncluso;
    }

    public String getEquipamentoIncluso() {
        return equipamentoIncluso;
    }

    public void setEquipamentoIncluso(String equipamentoIncluso) {
        this.equipamentoIncluso = equipamentoIncluso;
    }
}
