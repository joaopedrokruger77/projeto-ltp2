package com.agenciaturismo.dao;

import com.agenciaturismo.model.Cliente;
import com.agenciaturismo.model.PacoteViagem;
import com.agenciaturismo.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {
    private Connection connection;
    private ClienteDAO clienteDAO;
    private PacoteViagemDAO pacoteDAO;

    public ContratoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.clienteDAO = new ClienteDAO();
        this.pacoteDAO = new PacoteViagemDAO();
    }

    public boolean contratarPacote(int clienteId, int pacoteId) {
        String sql = "INSERT INTO contratos (cliente_id, pacote_id, data_contrato) VALUES (?, ?, CURRENT_DATE)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao contratar pacote: " + e.getMessage());
            return false;
        }
    }

    public boolean adicionarServicoContrato(int clienteId, int pacoteId, int servicoId) {
        String sql = "INSERT INTO contrato_servicos (cliente_id, pacote_id, servico_id) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.setInt(3, servicoId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar serviço ao contrato: " + e.getMessage());
            return false;
        }
    }

    public List<PacoteViagem> buscarPacotesPorCliente(int clienteId) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT p.* FROM pacotes_viagem p INNER JOIN contratos c ON p.id = c.pacote_id WHERE c.cliente_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PacoteViagem pacote = new PacoteViagem();
                    pacote.setId(rs.getInt("id"));
                    pacote.setNome(rs.getString("nome"));
                    pacote.setDestino(rs.getString("destino"));
                    pacote.setDescricao(rs.getString("descricao"));
                    pacote.setPreco(rs.getBigDecimal("preco"));
                    pacote.setDataInicio(rs.getDate("data_inicio"));
                    pacote.setDataFim(rs.getDate("data_fim"));
                    pacote.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
                    pacotes.add(pacote);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacotes do cliente: " + e.getMessage());
        }
        
        return pacotes;
    }

    public List<Cliente> buscarClientesPorPacote(int pacoteId) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.* FROM clientes c INNER JOIN contratos co ON c.id = co.cliente_id WHERE co.pacote_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacoteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = clienteDAO.buscarPorId(rs.getInt("id"));
                    if (cliente != null) {
                        clientes.add(cliente);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes do pacote: " + e.getMessage());
        }
        
        return clientes;
    }

    public boolean cancelarContrato(int clienteId, int pacoteId) {
        String sql = "DELETE FROM contratos WHERE cliente_id = ? AND pacote_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar contrato: " + e.getMessage());
            return false;
        }
    }
} 