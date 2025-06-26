package com.agenciaturismo.dao;

import com.agenciaturismo.model.ServicoAdicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoAdicionalDAO {
    private Connection connection;

    public ServicoAdicionalDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean inserir(ServicoAdicional servico) {
        String sql = "INSERT INTO servicos_adicionais (nome, descricao, preco, categoria) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getPreco());
            stmt.setString(4, servico.getCategoria());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        servico.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir serviço: " + e.getMessage());
        }
        return false;
    }

    public List<ServicoAdicional> listarTodos() {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos_adicionais";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ServicoAdicional servico = criarServicoFromResultSet(rs);
                servicos.add(servico);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }
        
        return servicos;
    }

    public ServicoAdicional buscarPorId(int id) {
        String sql = "SELECT * FROM servicos_adicionais WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarServicoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviço: " + e.getMessage());
        }
        
        return null;
    }

    public List<ServicoAdicional> buscarPorCategoria(String categoria) {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos_adicionais WHERE categoria LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + categoria + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    servicos.add(criarServicoFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviços por categoria: " + e.getMessage());
        }
        
        return servicos;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM servicos_adicionais WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir serviço: " + e.getMessage());
            return false;
        }
    }

    private ServicoAdicional criarServicoFromResultSet(ResultSet rs) throws SQLException {
        ServicoAdicional servico = new ServicoAdicional();
        servico.setId(rs.getInt("id"));
        servico.setNome(rs.getString("nome"));
        servico.setDescricao(rs.getString("descricao"));
        servico.setPreco(rs.getBigDecimal("preco"));
        servico.setCategoria(rs.getString("categoria"));
        return servico;
    }
} 