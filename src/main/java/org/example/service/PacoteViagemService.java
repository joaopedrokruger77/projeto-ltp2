package org.example.service;

import org.example.model.pacoteviagem.PacoteViagem;
import org.example.repository.ClientePacoteRepository;
import org.example.repository.PacoteViagemRepository;

import java.util.List;

public class PacoteViagemService {

    private PacoteViagemRepository pacoteRepository;
    private ClientePacoteRepository clientePacoteRepository;

    public PacoteViagemService(PacoteViagemRepository pacoteRepository, ClientePacoteRepository clientePacoteRepository) {
        this.pacoteRepository = pacoteRepository;
        this.clientePacoteRepository = clientePacoteRepository;
    }

    public void cadastrarPacote(PacoteViagem pacote) {
        if (pacote.getPreco() <= 0 || pacote.getDestino() == null) {
            throw new IllegalArgumentException("Pacote deve conter preço e destino.");
        }

        pacoteRepository.salvar(pacote);
    }

    public PacoteViagem buscarPorId(Long id) {
        return pacoteRepository.buscarPorId(id);
    }

    public List<PacoteViagem> listarPacotes() {
        return pacoteRepository.listarTodos();
    }

    public void excluirPacote(Long id) {
        if (clientePacoteRepository.existeRelacionamentoComPacote(id)) {
            throw new IllegalStateException("Não é possível excluir pacote com clientes associados.");
        }
        pacoteRepository.excluir(id);
    }
}

