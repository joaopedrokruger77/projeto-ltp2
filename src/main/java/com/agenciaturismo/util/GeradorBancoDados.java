package com.agenciaturismo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Esta classe gera o script SQL (DDL) para criar a estrutura do banco de dados
 * da agência de turismo, com base nos modelos de dados do projeto.
 */
public class GeradorBancoDados {

    private static final String SERVER_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_NAME = "agencia_viagens";
    private static final String USER = "root"; // Coloque seu usuário do MySQL aqui
    private static final String PASS = "ceub123456"; // <<<<<< COLOQUE SUA SENHA DO MYSQL AQUI

    public static void main(String[] args) {
        try {
            System.out.println("-> Iniciando configuração do banco de dados...");

            // 1. Criar o banco de dados
            criarDatabase();

            // 2. Criar as tabelas dentro do banco de dados
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

                    // Tabela de Associação: Cliente <-> Pacote
                    "CREATE TABLE IF NOT EXISTS cliente_pacote (" +
                            "    id_cliente INT," +
                            "    id_pacote INT," +
                            "    data_contratacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "    PRIMARY KEY (id_cliente, id_pacote)," +
                            "    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE," +
                            "    FOREIGN KEY (id_pacote) REFERENCES pacotes_viagem(id) ON DELETE RESTRICT" +
                            ");",

                    // Tabela de Associação: Pacote <-> Serviço
                    "CREATE TABLE IF NOT EXISTS pacote_servico (" +
                            "    id_pacote INT," +
                            "    id_servico INT," +
                            "    PRIMARY KEY (id_pacote, id_servico)," +
                            "    FOREIGN KEY (id_pacote) REFERENCES pacotes_viagem(id) ON DELETE CASCADE," +
                            "    FOREIGN KEY (id_servico) REFERENCES servicos_adicionais(id) ON DELETE CASCADE" +
                            ");"
            };

            for (String sql : sqlScripts) {
                stmt.execute(sql);
            }

            System.out.println("--> Tabelas criadas com sucesso.");
        }
    }
}