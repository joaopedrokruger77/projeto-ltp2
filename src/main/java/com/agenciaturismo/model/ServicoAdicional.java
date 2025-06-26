package com.agenciaturismo.model;

import java.math.BigDecimal;

public class ServicoAdicional {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;

    public ServicoAdicional() {}

    public ServicoAdicional(String nome, String descricao, BigDecimal preco, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return nome + " - " + categoria + " (R$ " + preco + ")";
    }
} 