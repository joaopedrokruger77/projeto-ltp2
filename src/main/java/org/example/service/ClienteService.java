package org.example.service;

import org.example.model.cliente.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public boolean removerCliente(Cliente cliente) {
        return clientes.remove(cliente);
    }

    public void limparClientes() {
        clientes.clear();
    }

    public int quantidadeClientes() {
        return clientes.size();
    }
}
