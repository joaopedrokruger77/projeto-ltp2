package com.agenciaturismo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacoteViagem {
    private int id;
    private String nome;
    private String destino;
    private String descricao;
    private BigDecimal preco;
    private Date dataInicio;
    private Date dataFim;
    private int vagasDisponiveis;
    private List<ServicoAdicional> servicosInclusos;
    private List<Cliente> clientesContratantes;

    public PacoteViagem() {
        this.servicosInclusos = new ArrayList<>();
        this.clientesContratantes = new ArrayList<>();
    }

    public PacoteViagem(String nome, String destino, String descricao, BigDecimal preco, 
                       Date dataInicio, Date dataFim, int vagasDisponiveis) {
        this.nome = nome;
        this.destino = destino;
        this.descricao = descricao;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.vagasDisponiveis = vagasDisponiveis;
        this.servicosInclusos = new ArrayList<>();
        this.clientesContratantes = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(int vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; }

    public List<ServicoAdicional> getServicosInclusos() { return servicosInclusos; }
    public void setServicosInclusos(List<ServicoAdicional> servicosInclusos) { 
        this.servicosInclusos = servicosInclusos; 
    }

    public List<Cliente> getClientesContratantes() { return clientesContratantes; }
    public void setClientesContratantes(List<Cliente> clientesContratantes) { 
        this.clientesContratantes = clientesContratantes; 
    }

    public void adicionarServico(ServicoAdicional servico) {
        this.servicosInclusos.add(servico);
    }

    public void adicionarCliente(Cliente cliente) {
        this.clientesContratantes.add(cliente);
        this.vagasDisponiveis--;
    }

    public BigDecimal calcularPrecoTotal() {
        BigDecimal total = preco;
        for (ServicoAdicional servico : servicosInclusos) {
            total = total.add(servico.getPreco());
        }
        return total;
    }

    @Override
    public String toString() {
        return nome + " - " + destino + " (R$ " + preco + ")";
    }
} 