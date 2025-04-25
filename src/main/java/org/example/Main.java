package org.example;

import org.example.model.cliente.Cliente;
import org.example.model.cliente.ClienteEstrangeiro;
import org.example.model.cliente.ClienteNacional;
import org.example.model.pacoteviagem.PacoteLuxo;
import org.example.model.pacoteviagem.TipoPacote;
import org.example.service.ClienteService;
import org.example.service.PacoteViagemService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClienteService clienteService = new ClienteService();
        PacoteViagemService pacoteService = new PacoteViagemService();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar cliente e pacote");
            System.out.println("2 - Listar todos os clientes");
            System.out.println("3 - Listar todos os pacotes");
            System.out.println("4 - Sair");
            System.out.println("5 - Ver clientes por pacote");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    Cliente cliente = cadastrarCliente(scanner);
                    if (cliente != null) {
                        clienteService.adicionarCliente(cliente);

                        PacoteLuxo pacote = cadastrarPacote(scanner);
                        pacoteService.adicionarPacote(pacote);

                        cliente.contratarPacote(pacote);

                        System.out.println("Cliente e pacote cadastrados com sucesso!");
                    }
                    break;
                case 2:
                    System.out.println("\nClientes cadastrados:");
                    clienteService.listarClientes().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("\nPacotes cadastrados:");
                    pacoteService.listarPacotes().forEach(System.out::println);
                    break;
                case 4:
                    continuar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                case 5:
                    System.out.println("\n=== Clientes por Pacote ===");
                    for (var pacote : pacoteService.listarPacotes()) {
                        System.out.println("\nPacote: " + pacote.getNome());
                        boolean encontrado = false;
                        for (var clienteAtual : clienteService.listarClientes()) {
                            if (clienteAtual.getPacotesContratados().contains(pacote)) {
                                System.out.println(" - " + clienteAtual.getNome());
                                encontrado = true;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Nenhum cliente contratou este pacote.");
                        }
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static Cliente cadastrarCliente(Scanner scanner) {
        System.out.println("\n=== Cadastro de Cliente ===");
        System.out.println("Tipo de cliente:");
        System.out.println("1 - Nacional");
        System.out.println("2 - Estrangeiro");
        System.out.print("Escolha uma opção: ");
        int tipoCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (tipoCliente == 1) {
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            return new ClienteNacional(nome, cpf, telefone, email);
        } else if (tipoCliente == 2) {
            System.out.print("Passaporte: ");
            String passaporte = scanner.nextLine();
            return new ClienteEstrangeiro(nome, passaporte, telefone, email);
        } else {
            System.out.println("Tipo de cliente inválido.");
            return null;
        }
    }

    private static PacoteLuxo cadastrarPacote(Scanner scanner) {
        System.out.println("\n=== Cadastro de Pacote ===");

        System.out.print("Nome do Pacote: ");
        String nomePacote = scanner.nextLine();

        System.out.print("Destino: ");
        String destino = scanner.nextLine();

        System.out.print("Duração (em dias): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());

        return new PacoteLuxo(nomePacote, destino, duracao, preco, TipoPacote.LUXO);
    }
}
