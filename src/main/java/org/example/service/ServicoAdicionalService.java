package org.example.service;

import org.example.model.servicoadicional.ServicoAdicional;
import org.example.repository.ServicoAdicionalRepository;

import java.util.List;

public class ServicoAdicionalService {

    private final ServicoAdicionalRepository servicoRepository;

    public ServicoAdicionalService(ServicoAdicionalRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void cadastrarServico(ServicoAdicional servico) {
        servicoRepository.salvar(servico);
    }

    public ServicoAdicional buscarPorId(Long id) {
        return servicoRepository.buscarPorId(id);
    }

    public List<ServicoAdicional> listarServicos() {
        return servicoRepository.listarTodos();
    }

    public void excluirServico(Long id) {
        servicoRepository.excluir(id);
    }
}

