package org.example.service;

import org.example.model.pacoteviagem.PacoteViagem;

import java.util.ArrayList;
import java.util.List;

public class PacoteViagemService {
    private List<PacoteViagem> pacotes = new ArrayList<>();

    public void adicionarPacote(PacoteViagem pacote) {
        pacotes.add(pacote);
    }

    public List<PacoteViagem> listarPacotes() {
        return pacotes;
    }
}