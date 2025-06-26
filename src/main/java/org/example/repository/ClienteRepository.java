package org.example.repository;

import org.example.model.cliente.Cliente;
import org.example.model.cliente.ClienteEstrangeiro;
import org.example.model.cliente.ClienteNacional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteRepository {

    private final Connection connection;

    public ClienteRepository(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Conexão não pode ser nula");
    }

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, email, cpf, passaporte) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());

            if (cliente instanceof ClienteNacional nacional) {
                stmt.setString(4, nacional.getCpf());
                stmt.setNull(5, Types.VARCHAR);
            } else if (cliente instanceof ClienteEstrangeiro estrangeiro) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setString(5, estrangeiro.getPassaporte());
            } else {
                throw new IllegalArgumentException("Tipo de cliente não suportado: " + cliente.getClass());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace(); // Em produção, use logging (Log4j, SLF4J etc)
        }
    }

    public Cliente buscarPorId(Long id) {
        // Implementação futura
        return null;
    }

    public List<Cliente> listarTodos() {
        // Implementação futura
        return new ArrayList<>();
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int afetados = stmt.executeUpdate();

            if (afetados == 0) {
                System.out.println("Nenhum cliente encontrado com ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
