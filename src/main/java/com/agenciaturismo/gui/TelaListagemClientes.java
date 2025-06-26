package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ClienteDAO;
import com.agenciaturismo.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaListagemClientes extends JDialog {
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private ClienteDAO clienteDAO;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public TelaListagemClientes(Frame parent) {
        super(parent, "Listagem de Clientes", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        configurarJanela();
        carregarClientes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel superior com título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));
        
        // Tabela
        String[] colunas = {"ID", "Nome", "E-mail", "Telefone", "Data Nascimento", "Tipo", "Documento"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaClientes.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabelaClientes.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir Selecionado");
        JButton btnFechar = new JButton("Fechar");

        btnAtualizar.addActionListener(e -> carregarClientes());
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirClienteSelecionado();
            }
        });

        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarClientes() {
        modeloTabela.setRowCount(0);
        
        List<Cliente> clientes = clienteDAO.listarTodos();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Cliente cliente : clientes) {
            Object[] linha = {
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                sdf.format(cliente.getDataNascimento()),
                cliente.getTipoCliente(),
                cliente.getDocumento()
            };
            modeloTabela.addRow(linha);
        }
        
        btnExcluir.setEnabled(false);
        
        // Listener para habilitar botão excluir quando uma linha for selecionada
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnExcluir.setEnabled(tabelaClientes.getSelectedRow() != -1);
            }
        });
    }

    private void excluirClienteSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir o cliente '" + nome + "'?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (clienteDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                carregarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarJanela() {
        setSize(900, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 