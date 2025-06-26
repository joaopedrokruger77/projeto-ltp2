package com.agenciaturismo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Cliente {
    protected int id;
    protected String nome;
    protected String email;
    protected String telefone;
    protected Date dataNascimento;
    protected List<PacoteViagem> pacotesContratados;

    public Cliente() {
        this.pacotesContratados = new ArrayList<>();
    }

    public Cliente(String nome, String email, String telefone, Date dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.pacotesContratados = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public List<PacoteViagem> getPacotesContratados() { return pacotesContratados; }
    public void setPacotesContratados(List<PacoteViagem> pacotesContratados) { 
        this.pacotesContratados = pacotesContratados; 
    }

    public void adicionarPacote(PacoteViagem pacote) {
        this.pacotesContratados.add(pacote);
    }

    public abstract String getTipoCliente();
    public abstract String getDocumento();
    
    @Override
    public String toString() {
        return nome + " (" + getTipoCliente() + ")";
    }
} 