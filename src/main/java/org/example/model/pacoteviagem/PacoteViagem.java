package org.example.model.pacoteviagem;

public class PacoteViagem {
    private String nome;
    private String destino;
    private int duracao;
    private double preco;
    private TipoPacote tipo;
    private String detalhes;

    public PacoteViagem(String nome, String destino, int duracao, double preco, TipoPacote tipo, String detalhes) {
        if (preco <= 0 || destino == null || destino.isEmpty()) {
            throw new IllegalArgumentException("Preço e destino são obrigatórios.");
        }
        this.nome = nome;
        this.destino = destino;
        this.duracao = duracao;
        this.preco = preco;
        this.tipo = tipo;
        this.detalhes = detalhes;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getDestino() {
        return destino;
    }

    public int getDuracao() {
        return duracao;
    }

    public double getPreco() {
        return preco;
    }

    public TipoPacote getTipo() {
        return tipo;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(TipoPacote tipo) {
        this.tipo = tipo;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}