package org.example.repository;

import org.example.model.pacoteviagem.PacoteViagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacoteViagemRepository {

    private Connection connection;

    public PacoteViagemRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(PacoteViagem pacote) {
        String sql = "INSERT INTO pacote_viagem (nome, destino, duracao, preco, tipo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pacote.getNome());
            stmt.setString(2, pacote.getDestino());
            stmt.setInt(3, pacote.getDuracao());
            stmt.setDouble(4, pacote.getPreco());
            stmt.setString(5, pacote.getTipo().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PacoteViagem buscarPorId(Long id) {
        return null;
    }

    public List<PacoteViagem> listarTodos() {
        return new ArrayList<>();
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM pacote_viagem WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

