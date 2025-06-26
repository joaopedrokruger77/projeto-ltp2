package com.agenciaturismo.gui;

import com.agenciaturismo.dao.PacoteViagemDAO;
import com.agenciaturismo.model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaListagemPacotes extends JDialog {
    private JTable tabelaPacotes;
    private DefaultTableModel modeloTabela;
    private PacoteViagemDAO pacoteDAO;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public TelaListagemPacotes(Frame parent) {
        super(parent, "Listagem de Pacotes de Viagem", true);
        this.pacoteDAO = new PacoteViagemDAO();
        initComponents();
        configurarJanela();
        carregarPacotes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela
        String[] colunas = {"ID", "Nome", "Destino", "Preço", "Data Início", "Data Fim", "Vagas"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaPacotes = new JTable(modeloTabela);
        tabelaPacotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPacotes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaPacotes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaPacotes.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaPacotes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaPacotes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaPacotes.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaPacotes.getColumnModel().getColumn(6).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPacotes);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir Selecionado");
        JButton btnFechar = new JButton("Fechar");

        btnAtualizar.addActionListener(e -> carregarPacotes());
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirPacoteSelecionado();
            }
        });

        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarPacotes() {
        modeloTabela.setRowCount(0);
        
        List<PacoteViagem> pacotes = pacoteDAO.listarTodos();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (PacoteViagem pacote : pacotes) {
            Object[] linha = {
                pacote.getId(),
                pacote.getNome(),
                pacote.getDestino(),
                "R$ " + pacote.getPreco(),
                sdf.format(pacote.getDataInicio()),
                sdf.format(pacote.getDataFim()),
                pacote.getVagasDisponiveis()
            };
            modeloTabela.addRow(linha);
        }
        
        btnExcluir.setEnabled(false);
        
        // Listener para habilitar botão excluir quando uma linha for selecionada
        tabelaPacotes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnExcluir.setEnabled(tabelaPacotes.getSelectedRow() != -1);
            }
        });
    }

    private void excluirPacoteSelecionado() {
        int linhaSelecionada = tabelaPacotes.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pacote para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        // Verificar se há clientes associados
        if (pacoteDAO.possuiClientesAssociados(id)) {
            JOptionPane.showMessageDialog(this, 
                "Não é possível excluir o pacote '" + nome + "' pois existem clientes associados a ele!", 
                "Exclusão não permitida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o pacote '" + nome + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (pacoteDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Pacote excluído com sucesso!");
                carregarPacotes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pacote!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarJanela() {
        setSize(900, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 