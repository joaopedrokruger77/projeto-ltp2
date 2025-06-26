package com.agenciaturismo.dao;

import com.agenciaturismo.model.PacoteViagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacoteViagemDAO {
    private Connection connection;

    public PacoteViagemDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean inserir(PacoteViagem pacote) {
        String sql = "INSERT INTO pacotes_viagem (nome, destino, descricao, preco, data_inicio, data_fim, vagas_disponiveis) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pacote.getNome());
            stmt.setString(2, pacote.getDestino());
            stmt.setString(3, pacote.getDescricao());
            stmt.setBigDecimal(4, pacote.getPreco());
            stmt.setDate(5, new java.sql.Date(pacote.getDataInicio().getTime()));
            stmt.setDate(6, new java.sql.Date(pacote.getDataFim().getTime()));
            stmt.setInt(7, pacote.getVagasDisponiveis());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pacote.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pacote: " + e.getMessage());
        }
        return false;
    }

    public List<PacoteViagem> listarTodos() {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM pacotes_viagem";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PacoteViagem pacote = criarPacoteFromResultSet(rs);
                pacotes.add(pacote);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pacotes: " + e.getMessage());
        }
        
        return pacotes;
    }

    public PacoteViagem buscarPorId(int id) {
        String sql = "SELECT * FROM pacotes_viagem WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarPacoteFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacote: " + e.getMessage());
        }
        
        return null;
    }

    public List<PacoteViagem> buscarPorDestino(String destino) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM pacotes_viagem WHERE destino LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + destino + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pacotes.add(criarPacoteFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacotes por destino: " + e.getMessage());
        }
        
        return pacotes;
    }

    public boolean excluir(int id) {
        // Verificar se há clientes associados ao pacote
        if (possuiClientesAssociados(id)) {
            System.err.println("Não é possível excluir pacote com clientes associados.");
            return false;
        }
        
        String sql = "DELETE FROM pacotes_viagem WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir pacote: " + e.getMessage());
            return false;
        }
    }

    public boolean possuiClientesAssociados(int pacoteId) {
        String sql = "SELECT COUNT(*) FROM contratos WHERE pacote_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacoteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar associações: " + e.getMessage());
        }
        
        return false;
    }

    private PacoteViagem criarPacoteFromResultSet(ResultSet rs) throws SQLException {
        PacoteViagem pacote = new PacoteViagem();
        pacote.setId(rs.getInt("id"));
        pacote.setNome(rs.getString("nome"));
        pacote.setDestino(rs.getString("destino"));
        pacote.setDescricao(rs.getString("descricao"));
        pacote.setPreco(rs.getBigDecimal("preco"));
        pacote.setDataInicio(rs.getDate("data_inicio"));
        pacote.setDataFim(rs.getDate("data_fim"));
        pacote.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
        return pacote;
    }
} 