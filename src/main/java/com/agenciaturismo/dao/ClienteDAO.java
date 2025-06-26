package com.agenciaturismo.dao;

import com.agenciaturismo.model.Cliente;
import com.agenciaturismo.model.ClienteNacional;
import com.agenciaturismo.model.ClienteEstrangeiro;
import com.agenciaturismo.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO() {

        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone, data_nascimento, tipo_cliente, cpf, passaporte, nacionalidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setDate(4, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(5, cliente.getTipoCliente());
            
            if (cliente instanceof ClienteNacional) {
                ClienteNacional clienteNacional = (ClienteNacional) cliente;
                stmt.setString(6, clienteNacional.getCpf());
                stmt.setString(7, null);
                stmt.setString(8, null);
            } else if (cliente instanceof ClienteEstrangeiro) {
                ClienteEstrangeiro clienteEstrangeiro = (ClienteEstrangeiro) cliente;
                stmt.setString(6, null);
                stmt.setString(7, clienteEstrangeiro.getPassaporte());
                stmt.setString(8, clienteEstrangeiro.getNacionalidade());
            }
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = criarClienteFromResultSet(rs);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        
        return clientes;
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarClienteFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        
        return null;
    }

    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = criarClienteFromResultSet(rs);
                    if (cliente != null) {
                        clientes.add(cliente);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes por nome: " + e.getMessage());
        }
        
        return clientes;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente criarClienteFromResultSet(ResultSet rs) throws SQLException {
        String tipoCliente = rs.getString("tipo_cliente");
        Cliente cliente;
        
        if ("Nacional".equals(tipoCliente)) {
            cliente = new ClienteNacional();
            ((ClienteNacional) cliente).setCpf(rs.getString("cpf"));
        } else {
            cliente = new ClienteEstrangeiro();
            ((ClienteEstrangeiro) cliente).setPassaporte(rs.getString("passaporte"));
            ((ClienteEstrangeiro) cliente).setNacionalidade(rs.getString("nacionalidade"));
        }
        
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setDataNascimento(rs.getDate("data_nascimento"));
        
        return cliente;
    }
} 