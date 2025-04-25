package org.example;

import org.example.model.cliente.ClienteNacional;
import org.example.model.pacoteviagem.PacoteLuxo;
import org.example.model.pacoteviagem.TipoPacote;
import org.example.service.ClienteService;
import org.example.service.PacoteViagemService;

public class Main {
    public static void main(String[] args) {

        ClienteService clienteService = new ClienteService();
        PacoteViagemService pacoteService = new PacoteViagemService();

        ClienteNacional cliente1 = new ClienteNacional("João Silva", "123.456.789-00", "11999999999", "joao@email.com") {
            @Override
            public String getEmail() {
                return "";
            }

            @Override
            public String getPassaporte() {
                return "";
            }

            @Override
            public String getCpf() {
                return "";
            }

            @Override
            public String getTelefone() {
                return "";
            }
        };
        clienteService.adicionarCliente(cliente1);

        PacoteLuxo pacote1 = new PacoteLuxo("Pacote Caribe", "Caribe", 7, 5000.0, TipoPacote.LUXO);
        pacoteService.adicionarPacote(pacote1);

        cliente1.contratarPacote(pacote1);

        System.out.println("\nClientes cadastrados:");
        clienteService.listarClientes().forEach(System.out::println);

        System.out.println("\nPacotes cadastrados:");
        pacoteService.listarPacotes().forEach(System.out::println);

        System.out.println("\nPacotes contratados pelo cliente " + cliente1.getNome() + ":");
        cliente1.getPacotesContratados().forEach(System.out::println);
    }
}
