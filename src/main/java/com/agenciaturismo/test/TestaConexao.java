package com.agenciaturismo.test;

import com.agenciaturismo.util.DatabaseConnection;

import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão com o banco estabelecida com sucesso!");
                connection.close();
            } else {
                System.out.println("Falha ao conectar com o banco.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
