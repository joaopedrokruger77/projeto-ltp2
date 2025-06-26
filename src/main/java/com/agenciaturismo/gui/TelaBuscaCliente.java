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

public class TelaBuscaCliente extends JDialog {
    private JTextField txtNomeBusca;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private ClienteDAO clienteDAO;

    public TelaBuscaCliente(Frame parent) {
        super(parent, "Buscar Cliente", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout());
        painelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Cliente"));
        
        painelBusca.add(new JLabel("Nome:"));
        txtNomeBusca = new JTextField(20);
        painelBusca.add(txtNomeBusca);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });
        painelBusca.add(btnBuscar);

        add(painelBusca, BorderLayout.NORTH);

        // Tabela de resultados
        String[] colunas = {"ID", "Nome", "E-mail", "Telefone", "Data Nascimento", "Tipo", "Documento"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setPreferredSize(new Dimension(700, 350));
        
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Listener para Enter no campo de busca
        txtNomeBusca.addActionListener(e -> buscarClientes());
    }

    private void buscarClientes() {
        String nomeBusca = txtNomeBusca.getText().trim();
        
        if (nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtNomeBusca.requestFocus();
            return;
        }

        modeloTabela.setRowCount(0);
        
        List<Cliente> clientes = clienteDAO.buscarPorNome(nomeBusca);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado com esse nome!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
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
        }
    }

    private void configurarJanela() {
        setSize(750, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 