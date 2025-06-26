package org.example.service;

import org.example.model.pacoteviagem.PacoteViagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PacoteViagemService {

    private final List<PacoteViagem> pacotes = new ArrayList<>();

    public void adicionarPacote(PacoteViagem pacote) {
        if (pacote == null) {
            throw new IllegalArgumentException("O pacote não pode ser nulo.");
        }
        pacotes.add(pacote);
    }

    public List<PacoteViagem> listarPacotes() {
        return Collections.unmodifiableList(pacotes);
    }

    public void limparPacotes() {
        pacotes.clear();
    }

    public boolean removerPacote(PacoteViagem pacote) {
        return pacotes.remove(pacote);
    }

    public int quantidadePacotes() {
        return pacotes.size();
    }
}
