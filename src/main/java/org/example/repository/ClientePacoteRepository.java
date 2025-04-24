package org.example.repository;

import org.example.model.cliente.Cliente;
import org.example.model.pacoteviagem.PacoteViagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientePacoteRepository {

    private Connection connection;

    public ClientePacoteRepository(Connection connection) {
        this.connection = connection;
    }

    public void contratarPacote(Long clienteId, Long pacoteId, List<Long> servicoIds) {
        try {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO cliente_pacote (cliente_id, pacote_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, clienteId);
                stmt.setLong(2, pacoteId);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                Long idClientePacote = null;
                if (rs.next()) {
                    idClientePacote = rs.getLong(1);
                }

                if (idClientePacote != null && servicoIds != null) {
                    String servicoSql = "INSERT INTO cliente_pacote_servico (cliente_pacote_id, servico_id) VALUES (?, ?)";
                    try (PreparedStatement servicoStmt = connection.prepareStatement(servicoSql)) {
                        for (Long servicoId : servicoIds) {
                            servicoStmt.setLong(1, idClientePacote);
                            servicoStmt.setLong(2, servicoId);
                            servicoStmt.addBatch();
                        }
                        servicoStmt.executeBatch();
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeRelacionamentoComPacote(Long pacoteId) {
        String sql = "SELECT COUNT(*) FROM cliente_pacote WHERE pacote_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, pacoteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PacoteViagem> listarPacotesPorCliente(Long clienteId) {
        return new ArrayList<>();
    }

    public List<Cliente> listarClientesPorPacote(Long pacoteId) {
        return new ArrayList<>();
    }
}