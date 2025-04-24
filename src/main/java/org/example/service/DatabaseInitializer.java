package org.example.service;


import org.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void main(String[] args) {
        try (Connection rootConn = DatabaseConfig.connectRoot();
             Statement stmt = rootConn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS agencia_viagens");

            try (Connection dbConn = DatabaseConfig.connect("agencia_viagens");
                 Statement dbStmt = dbConn.createStatement()) {

                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS clientes (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        tipo ENUM('NACIONAL', 'ESTRANGEIRO') NOT NULL,
                        documento VARCHAR(20) NOT NULL UNIQUE, 
                        telefone VARCHAR(20),
                        email VARCHAR(100)
                    );
                """);
                dbStmt.executeUpdate("""
                
                        CREATE TABLE IF NOT EXISTS pacotes_viagem (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    destino VARCHAR(100) NOT NULL,
                    duracao INT NOT NULL,
                    preco DECIMAL(10,2) NOT NULL,
                    tipo ENUM('AVENTURA', 'LUXO', 'CULTURAL') NOT NULL,
                    detalhes TEXT
                );    
                """);
                dbStmt.executeUpdate("""
                
                        CREATE TABLE IF NOT EXISTS servicos_adicionais (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    preco DECIMAL(10,2) NOT NULL
                );
                """);
                dbStmt.executeUpdate("""
                
                        CREATE TABLE IF NOT EXISTS cliente_pacote (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    cliente_id INT NOT NULL,
                    pacote_id INT NOT NULL,
                    data_contratacao DATE NOT NULL,
                    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                    FOREIGN KEY (pacote_id) REFERENCES pacotes_viagem(id)
                );
                """);
                dbStmt.executeUpdate("""
                
                        CREATE TABLE IF NOT EXISTS pedido_servico (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    cliente_pacote_id INT NOT NULL,
                    servico_id INT NOT NULL,
                    FOREIGN KEY (cliente_pacote_id) REFERENCES cliente_pacote(id),
                    FOREIGN KEY (servico_id) REFERENCES servicos_adicionais(id)
                );
        
                """);

                // Você pode repetir para as outras tabelas...
                System.out.println("Banco criado com sucesso!");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
