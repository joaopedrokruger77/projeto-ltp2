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
                        tipo ENUM('nacional', 'estrangeiro') NOT NULL,
                        cpf VARCHAR(14),
                        passaporte VARCHAR(20),
                        telefone VARCHAR(20),
                        email VARCHAR(100),
                        CONSTRAINT chk_documento CHECK (
                            (tipo = 'nacional' AND cpf IS NOT NULL AND passaporte IS NULL) OR
                            (tipo = 'estrangeiro' AND passaporte IS NOT NULL AND cpf IS NULL)
                        )
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
