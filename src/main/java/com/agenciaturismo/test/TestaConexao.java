package com.agenciaturismo.test;

import com.agenciaturismo.util.DatabaseConnection;
import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexão com o banco de dados realizada com sucesso!");
            } else {
                System.out.println("❌ Falha na conexão com o banco de dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
