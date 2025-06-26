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

    public void contratarPacote(Long clienteId, Long pacoteId, List<Long> servicoIds) {
        clientePacoteRepository.contratarPacote(clienteId, pacoteId, servicoIds);
    }

    public List<PacoteViagem> buscarPacotesPorCliente(Long clienteId) {
        return clientePacoteRepository.listarPacotesPorCliente(clienteId);
    }

    public List<Cliente> buscarClientesPorPacote(Long pacoteId) {
        return clientePacoteRepository.listarClientesPorPacote(pacoteId);
    }


    public List<Long> listarServicosDoPedido(Long clienteId, Long pacoteId) {
        return Collections.emptyList(); // ou lance uma exceção se preferir
    }
}