package org.example.model;

import org.example.model.cliente.Cliente;
import org.example.model.pacoteviagem.PacoteViagem;
import org.example.model.servicoadicional.ServicoAdicional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClientePacote {
    private int id;
    private Cliente cliente;
    private PacoteViagem pacote;
    private final List<ServicoAdicional> servicos;

    public ClientePacote(Cliente cliente, PacoteViagem pacote) {
        this.cliente = Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
        this.pacote = Objects.requireNonNull(pacote, "Pacote não pode ser nulo");
        this.servicos = new ArrayList<>();
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
        this.cliente = Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
    }

    public PacoteViagem getPacote() {
        return pacote;
    }

    public void setPacote(PacoteViagem pacote) {
        this.pacote = Objects.requireNonNull(pacote, "Pacote não pode ser nulo");
    }

    public List<ServicoAdicional> getServicos() {
        return Collections.unmodifiableList(servicos);
    }

    public void adicionarServico(ServicoAdicional servico) {
        if (servico != null && !servicos.contains(servico)) {
            servicos.add(servico);
        }
    }

    public void removerServico(ServicoAdicional servico) {
        servicos.remove(servico);
    }

    public void limparServicos() {
        servicos.clear();
    }
}


