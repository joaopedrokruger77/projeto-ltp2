package org.example.model.cliente;

import org.example.model.pacoteviagem.PacoteViagem;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private List<PacoteViagem> pacotesContratados = new ArrayList<>();

    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public void contratarPacote(PacoteViagem pacote) {
        pacotesContratados.add(pacote);
    }

    public List<PacoteViagem> getPacotesContratados() {
        return pacotesContratados;
    }

    public String getNome() {
        return nome;
    }

    public abstract String getIdentificacao();

    @Override
    public String toString() {
        return nome + " - " + getIdentificacao() + " - " + telefone + " - " + email;
    }

    public abstract String getEmail();

    public abstract String getPassaporte();

    public abstract String getCpf();

    public abstract String getTelefone();
}
