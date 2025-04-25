package org.example.model.pacoteviagem;

public abstract class PacoteViagem {
    private String nome;
    private String destino;
    private int duracao;
    private double preco;
    private TipoPacote tipo;

    public PacoteViagem(String nome, String destino, int duracao, double preco, TipoPacote tipo) {
        this.nome = nome;
        this.destino = destino;
        this.duracao = duracao;
        this.preco = preco;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public String getDestino() { return destino; }
    public int getDuracao() { return duracao; }
    public double getPreco() { return preco; }
    public Enum<TipoPacote> getTipo() { return tipo; }

    public abstract String getDetalhes();

    @Override
    public String toString() {
        return nome + " - " + destino + " - " + duracao + " dias - R$" + preco + " (" + tipo + ")";
    }
}


