package org.example.model;

public abstract class ServicoAdicional {
    protected String nome;
    protected double preco;

    public ServicoAdicional(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public abstract String getDescricao();

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}

