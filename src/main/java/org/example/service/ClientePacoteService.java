package org.example.service;

import org.example.model.cliente.Cliente;
import org.example.model.pacoteviagem.PacoteViagem;
import org.example.repository.ClientePacoteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientePacoteService {

    private final ClientePacoteRepository clientePacoteRepository;

    public ClientePacoteService(ClientePacoteRepository clientePacoteRepository) {
        this.clientePacoteRepository = clientePacoteRepository;
    }

    // Adaptado para aceitar lista de IDs de serviços
    public void contratarPacote(Long clienteId, Long pacoteId, List<Long> servicoIds) {
        clientePacoteRepository.contratarPacote(clienteId, pacoteId, servicoIds);
    }

    // Retorna pacotes contratados por cliente
    public List<PacoteViagem> buscarPacotesPorCliente(Long clienteId) {
        return clientePacoteRepository.listarPacotesPorCliente(clienteId);
    }

    // Retorna clientes que contrataram determinado pacote
    public List<Cliente> buscarClientesPorPacote(Long pacoteId) {
        return clientePacoteRepository.listarClientesPorPacote(pacoteId);
    }

    // Por enquanto retorna uma lista vazia (até implementar isso no repo)
    public List<Long> listarServicosDoPedido(Long clienteId, Long pacoteId) {
        return Collections.emptyList(); // ou lance uma exceção se preferir
    }
}
