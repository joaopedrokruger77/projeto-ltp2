package org.example.service;

import org.example.model.cliente.Cliente;
import org.example.model.cliente.ClienteEstrangeiro;
import org.example.model.cliente.ClienteNacional;
import org.example.repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void cadastrarCliente(Cliente cliente) {
        if (cliente instanceof ClienteNacional && ((ClienteNacional) cliente).getCpf() == null) {
            throw new IllegalArgumentException("Clientes nacionais devem informar o CPF.");
        }

        if (cliente instanceof ClienteEstrangeiro && ((ClienteEstrangeiro) cliente).getPassaporte() == null) {
            throw new IllegalArgumentException("Clientes estrangeiros devem informar o passaporte.");
        }

        clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodos();
    }

    public void excluirCliente(Long id) {
        clienteRepository.excluir(id);
    }
}

