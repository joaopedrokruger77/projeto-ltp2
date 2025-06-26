package com.agenciaturismo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe funcional que se conecta diretamente ao MySQL para criar e configurar
 * o banco de dados e as tabelas necessárias para a aplicação.
 * ESTA VERSÃO CRIA A TABELA 'contratos' CORRETAMENTE.
 */
public class GeradorBancoDados {

    // --- DADOS DE CONEXÃO ---
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_NAME = "agencia_viagens";
    private static final String USER = "root";
    private static final String PASS = "ceub123456"; // Sua senha do MySQL

    public static void main(String[] args) {
        try {
            System.out.println("-> Iniciando configuração do banco de dados...");

            // 1. Garante que o banco de dados exista
            criarDatabase();

            // 2. Cria as tabelas dentro do banco
            criarTabelas();

            System.out.println("\n[SUCESSO] Configuração do banco de dados finalizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("\n[ERRO] Ocorreu um erro durante a configuração do banco de dados.");
            e.printStackTrace();
        }
    }

    private static void criarDatabase() throws SQLException {
        System.out.println("--> Conectando ao servidor MySQL para criar o banco de dados...");
        // Conexão inicial sem especificar um banco de dados
        try (Connection conn = DriverManager.getConnection(SERVER_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sqlCreateDb = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
            stmt.executeUpdate(sqlCreateDb);

            System.out.println("--> Banco de dados '" + DB_NAME + "' garantido com sucesso.");
        }
    }

    private static void criarTabelas() throws SQLException {
        System.out.println("--> Conectando ao banco '" + DB_NAME + "' para criar as tabelas...");
        String dbUrl = SERVER_URL + "/" + DB_NAME;

        try (Connection conn = DriverManager.getConnection(dbUrl, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("--> Executando scripts de criação de tabelas...");

            String[] sqlScripts = {
                    // Tabela Clientes
                    "CREATE TABLE IF NOT EXISTS clientes (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    nome VARCHAR(255) NOT NULL," +
                            "    email VARCHAR(255) NOT NULL UNIQUE," +
                            "    telefone VARCHAR(20)," +
                            "    data_nascimento DATE," +
                            "    tipo_cliente ENUM('Nacional', 'Estrangeiro') NOT NULL," +
                            "    cpf VARCHAR(11) UNIQUE," +
                            "    passaporte VARCHAR(15) UNIQUE," +
                            "    nacionalidade VARCHAR(100)" +
                            ");",

                    // Tabela Pacotes de Viagem
                    "CREATE TABLE IF NOT EXISTS pacotes_viagem (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    nome VARCHAR(255) NOT NULL," +
                            "    destino VARCHAR(255) NOT NULL," +
                            "    descricao TEXT," +
                            "    preco DECIMAL(10, 2) NOT NULL," +
                            "    data_inicio DATE," +
                            "    data_fim DATE," +
                            "    vagas_disponiveis INT" +
                            ");",

                    // Tabela Serviços Adicionais
                    "CREATE TABLE IF NOT EXISTS servicos_adicionais (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    nome VARCHAR(255) NOT NULL," +
                            "    descricao TEXT," +
                            "    preco DECIMAL(10, 2) NOT NULL," +
                            "    categoria VARCHAR(100)" +
                            ");",

                    // Tabela de Associação: Pacote <-> Serviço
                    "CREATE TABLE IF NOT EXISTS pacote_servico (" +
                            "    pacote_id INT," +
                            "    servico_id INT," +
                            "    PRIMARY KEY (pacote_id, servico_id)," +
                            "    FOREIGN KEY (pacote_id) REFERENCES pacotes_viagem(id) ON DELETE CASCADE," +
                            "    FOREIGN KEY (servico_id) REFERENCES servicos_adicionais(id) ON DELETE CASCADE" +
                            ");",

                    // ***** TABELA DE CONTRATOS CORRIGIDA *****
                    // Esta tabela armazena a relação entre um cliente e um pacote contratado.
                    "CREATE TABLE IF NOT EXISTS contratos (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    cliente_id INT NOT NULL," +
                            "    pacote_id INT NOT NULL," +
                            "    data_contrato TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE," +
                            "    FOREIGN KEY (pacote_id) REFERENCES pacotes_viagem(id) ON DELETE RESTRICT" +
                            ");"
            };

            for (String sql : sqlScripts) {
                // System.out.println("Executando: " + sql); // Descomente para debug
                stmt.execute(sql);
            }

            System.out.println("--> Tabelas criadas com sucesso.");
        }
    }
}