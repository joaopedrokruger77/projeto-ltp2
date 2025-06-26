package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ServicoAdicionalDAO;
import com.agenciaturismo.model.ServicoAdicional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListagemServicos extends JDialog {
    private JTable tabelaServicos;
    private DefaultTableModel modeloTabela;
    private ServicoAdicionalDAO servicoDAO;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public TelaListagemServicos(Frame parent) {
        super(parent, "Listagem de Serviços Adicionais", true);
        this.servicoDAO = new ServicoAdicionalDAO();
        initComponents();
        configurarJanela();
        carregarServicos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabela
        String[] colunas = {"ID", "Nome", "Categoria", "Preço", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaServicos = new JTable(modeloTabela);
        tabelaServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaServicos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaServicos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaServicos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaServicos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaServicos.getColumnModel().getColumn(4).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(tabelaServicos);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir Selecionado");
        JButton btnFechar = new JButton("Fechar");

        btnAtualizar.addActionListener(e -> carregarServicos());
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirServicoSelecionado();
            }
        });

        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarServicos() {
        modeloTabela.setRowCount(0);
        
        List<ServicoAdicional> servicos = servicoDAO.listarTodos();
        
        for (ServicoAdicional servico : servicos) {
            Object[] linha = {
                servico.getId(),
                servico.getNome(),
                servico.getCategoria(),
                "R$ " + servico.getPreco(),
                servico.getDescricao()
            };
            modeloTabela.addRow(linha);
        }
        
        btnExcluir.setEnabled(false);
        
        // Listener para habilitar botão excluir quando uma linha for selecionada
        tabelaServicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnExcluir.setEnabled(tabelaServicos.getSelectedRow() != -1);
            }
        });
    }

    private void excluirServicoSelecionado() {
        int linhaSelecionada = tabelaServicos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o serviço '" + nome + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (servicoDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
                carregarServicos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarJanela() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 