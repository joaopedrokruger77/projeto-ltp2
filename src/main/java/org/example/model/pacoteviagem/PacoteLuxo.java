package org.example.model.pacoteviagem;

import org.example.model.pacoteviagem.PacoteViagem;
import org.example.model.pacoteviagem.TipoPacote;

public class PacoteLuxo extends PacoteViagem {
    public PacoteLuxo(String nome, String destino, int duracao, double preco, TipoPacote tipo) {
        super(nome, destino, duracao, preco, tipo);
    }

    @Override
    public String getDetalhes() {
        return "Pacote de Luxo: inclui hotel 5 estrelas, refeições e passeios exclusivos.";
    }
}
