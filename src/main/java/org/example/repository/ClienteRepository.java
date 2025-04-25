package org.example.repository;

import org.example.model.cliente.Cliente;
import org.example.model.cliente.ClienteEstrangeiro;
import org.example.model.cliente.ClienteNacional;

import java.sql.*;
import java.util.*;

public class ClienteRepository {

    private Connection connection;

    public ClienteRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, email, cpf, passaporte) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());

            if (cliente instanceof ClienteNacional) {
                stmt.setString(4, ((ClienteNacional) cliente).getCpf());
                stmt.setNull(5, Types.VARCHAR);
            } else if (cliente instanceof ClienteEstrangeiro) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setString(5, ((ClienteEstrangeiro) cliente).getPassaporte());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente buscarPorId(Long id) {
        return null;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>();
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
