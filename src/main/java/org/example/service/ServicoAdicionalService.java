package org.example.service;

import org.example.model.servicoadicional.ServicoAdicional;
import org.example.repository.ServicoAdicionalRepository;

import java.util.List;
import java.util.Objects;

public class ServicoAdicionalService {

    private final ServicoAdicionalRepository servicoRepository;

    public ServicoAdicionalService(ServicoAdicionalRepository servicoRepository) {
        this.servicoRepository = Objects.requireNonNull(servicoRepository, "Repositório não pode ser nulo");
    }

    public void cadastrarServico(ServicoAdicional servico) {
        validarServico(servico);
        servicoRepository.salvar(servico);
    }

    public ServicoAdicional buscarPorId(Long id) {
        validarId(id);
        return servicoRepository.buscarPorId(id);
    }

    public List<ServicoAdicional> listarServicos() {
        return servicoRepository.listarTodos();
    }

    public void excluirServico(Long id) {
        validarId(id);
        servicoRepository.excluir(id);
    }

    // -------------------
    // Métodos auxiliares
    // -------------------

    private void validarServico(ServicoAdicional servico) {
        if (servico == null) {
            throw new IllegalArgumentException("O serviço não pode ser nulo.");
        }

        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do serviço é obrigatório.");
        }

        if (servico.getPreco() < 0) {
            throw new IllegalArgumentException("O preço do serviço não pode ser negativo.");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }
}
