package org.example.model;

public class Translado extends ServicoAdicional {
    private String tipoVeiculo;

    public Translado(String nome, double preco, String tipoVeiculo) {
        super(nome, preco);
        this.tipoVeiculo = tipoVeiculo;
    }

    @Override
    public String getDescricao() {
        return "Translado em veículo tipo: " + tipoVeiculo;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
}
