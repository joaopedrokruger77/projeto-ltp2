package org.example.repository;

import org.example.model.servicoadicional.ServicoAdicional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoAdicionalRepository {

    private Connection connection;

    public ServicoAdicionalRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(ServicoAdicional servico) {
        String sql = "INSERT INTO servico_adicional (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getPreco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ServicoAdicional buscarPorId(Long id) {
        return null;
    }

    public List<ServicoAdicional> listarTodos() {
        return new ArrayList<>();
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM servico_adicional WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}