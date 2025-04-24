package org.example.model;

import org.example.model.cliente.Cliente;
import org.example.model.pacoteviagem.PacoteViagem;
import org.example.model.servicoadicional.ServicoAdicional;

import java.util.ArrayList;
import java.util.List;

public class ClientePacote {
    private int id;
    private Cliente cliente;
    private PacoteViagem pacote;
    private List<ServicoAdicional> servicos = new ArrayList<>();

    public ClientePacote(Cliente cliente, PacoteViagem pacote) {
        this.cliente = cliente;
        this.pacote = pacote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PacoteViagem getPacote() {
        return pacote;
    }

    public void setPacote(PacoteViagem pacote) {
        this.pacote = pacote;
    }

    public List<ServicoAdicional> getServicos() {
        return servicos;
    }

    public void adicionarServico(ServicoAdicional servico) {
        this.servicos.add(servico);
    }

    public void removerServico(ServicoAdicional servico) {
        this.servicos.remove(servico);
    }
}

